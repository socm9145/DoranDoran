package com.purple.hello.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dao.QuestionDAO;
import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.AwsS3DTO;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.tool.HistoryMinMaxDTO;
import com.purple.hello.dto.tool.HistoryTypeDTO;
import com.purple.hello.dto.tool.HistoryTypePythonDTO;
import com.purple.hello.dto.tool.MemberDTO;
import com.purple.hello.encoder.PasswordEncoder;
import com.purple.hello.entity.Room;
import com.purple.hello.generator.RoomCode;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.service.AwsS3Service;
import com.purple.hello.service.RoomService;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomCode roomCode;
    @Autowired
    private final RoomDAO roomDAO;
    @Autowired
    private final HistoryDAO historyDAO;
    @Autowired
    private final QuestionDAO questionDAO;
    @Autowired
    private final QuestionRepo questionRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final PythonInterpreter interpreter;
    private final AwsS3Service awsS3Service;

    public RoomServiceImpl(RoomDAO roomDAO, PasswordEncoder passwordEncoder, HistoryDAO historyDAO, QuestionRepo questionRepo, QuestionDAO questionDAO, PythonInterpreter interpreter, AwsS3Service awsS3Service){
        this.roomDAO = roomDAO;
        this.passwordEncoder = passwordEncoder;
        this.historyDAO = historyDAO;
        this.questionRepo = questionRepo;
        this.questionDAO = questionDAO;
        this.interpreter = interpreter;
        this.awsS3Service = awsS3Service;
    }
    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) throws Exception{
        return this.roomDAO.readRoomByUserId(userId);
    }

    @Override
    public CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) throws Exception{
        createUserRoomInDTO.setRoomPassword(passwordEncoder.encode(createUserRoomInDTO.getRoomPassword()));
        return this.roomDAO.createRoom(createUserRoomInDTO);
    }

    @Override
    public boolean comparePasswordByRoomCode(long roomId, String password) throws Exception{
        String storedPassword = this.roomDAO.comparePasswordByRoomCode(roomId);

        return passwordEncoder.matches(password, storedPassword);
    }

    @Override
    public ReadUserRoomJoinOutDTO readUserRoomJoinByRoomCode(String roomCode) throws Exception{
        return this.roomDAO.readUserRoomJoinByRoomCode(roomCode);
    }

    @Override
    @Transactional
    public boolean updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO) throws Exception {
        updateRoomPasswordInDTO.setRoomPassword(passwordEncoder.encode(updateRoomPasswordInDTO.getRoomPassword()));
        return this.roomDAO.updateRoomPassword(updateRoomPasswordInDTO);
    }

    public ReadRoomCodeOutDTO readRoomCodeByRoomId(long roomId) throws Exception{
        String url = roomDAO.readRoomCodeByRoomId(roomId);

        Instant currentTime = Instant.now();
        ReadRoomCodeOutDTO readRoomCodeOutDTO = new ReadRoomCodeOutDTO();
        if(url != null && url.length() > 0){
            String createdTimeString = roomCode.getTime(url);
            Instant createdTime = Instant.parse(createdTimeString);
            Duration duration = Duration.between(createdTime, currentTime);
            if(duration.compareTo(Duration.ofSeconds(30)) > 0){
                String newUrl = saveAndResult(roomId, currentTime);
                readRoomCodeOutDTO.setRoomCode(newUrl);
            }else{
                readRoomCodeOutDTO.setRoomCode(url);
            }
            return readRoomCodeOutDTO;
        }
        String newUrl = saveAndResult(roomId, currentTime);
        readRoomCodeOutDTO.setRoomCode(newUrl);
        return readRoomCodeOutDTO;
    }

    @Override
    public void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO) {
        roomDAO.updateRoomCodeByRoomId(updateRoomCodeInDTO);
    }
    private String saveAndResult(long roomId, Instant time) throws Exception{
        String newUrl = roomCode.makeUrl(roomId, time);
        UpdateRoomCodeInDTO updateRoomCodeInDTO = new UpdateRoomCodeInDTO();
        updateRoomCodeInDTO.setRoomId(roomId);
        updateRoomCodeInDTO.setRoomCode(newUrl);

        updateRoomCodeByRoomId(updateRoomCodeInDTO);
        return newUrl;
    }
    public boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) throws Exception{
        boolean isDeleted = this.roomDAO.deleteRoom(deleteRoomInDTO);
        if(isDeleted){
            String dirName = "feed/" + deleteRoomInDTO.getRoomId();
            awsS3Service.removeDirectory(dirName);
        }
        return isDeleted;
    }

    public void createQuestion() throws Exception{
        List<Room> roomList = roomDAO.getRoom();
        try{
            if(roomList.size() == 0)
                throw new NullPointerException("Don't Created Room");


            LocalDate currentDate = LocalDate.now();
            CreateQuestionInDTO createQuestionInDTO;
            List<Long> roomListIdx = new ArrayList<>();

            for(Room room : roomList) {
                LocalDate createdAt = room.getCreateAt().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                long result = currentDate.getDayOfYear() - createdAt.getDayOfYear();

                if (result < 9) {    // 방이 생성된지 10일 이전
                    long totalQuestion = questionRepo.count() - 1;
                    long questionId = (result % totalQuestion) + 2;

                    createQuestionInDTO = CreateQuestionInDTO.builder()
                            .roomId(room.getRoomId())
                            .no(questionId)
                            .build();
                    historyDAO.createHistory(createQuestionInDTO);
                } else {              // 방이 생선된지 10일 이후
                    roomListIdx.add(room.getRoomId());
                }
            }
            // 10일 이후 이면
            if(roomListIdx.size() != 0){
                // 각 방마다 daily, game, know 타입이 몇 개 있는지
                Map<Long, List<HistoryTypeDTO>> resultMapType = roomDAO.getHistoryTypeCount(roomListIdx);
                // 각 방마다 daily, game, know 타입에 대해서 포스트가 몇 개 있는지 추출
                Map<Long, List<HistoryTypeDTO>> resultMapFeed = roomDAO.getHistoryTypeFeedCount(roomListIdx);
                // 각 방마다 타입에 최소 인덱스와 최대 인덱스 그리고 현재 인덱스를 출력한다.
                Map<Long, List<HistoryMinMaxDTO>> resultMapMinMax = roomDAO.getHistoryMinMax(roomListIdx);

                Map<Long, HistoryTypePythonDTO> pyDTO = new HashMap<>();
                for(Long l : roomListIdx){
                    HistoryTypePythonDTO historyTypePythonDTO = HistoryTypePythonDTO.builder()
                            .types(resultMapType.get(l))
                            .feeds(resultMapFeed.get(l))
                            .minMaxCurrent(resultMapMinMax.get(l))
                            .build();
                    pyDTO.put(l, historyTypePythonDTO);
                }
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(pyDTO);
                System.out.println(json);

                interpreter.execfile("src/main/java/com/purple/hello/python/test.py");
                interpreter.exec("print('Hello, world!')");
                PyObject jsonString = Py.java2py(json);
                PyObject resultHistory = interpreter.get("testFunc").__call__(jsonString);
                Long resultRoomId = (Long) Py.tojava(resultHistory, Long.class);
            }

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReadRoomQuestionOutDTO readRoomQuestionByRoomIdAndUserId(long roomId, long userId) throws Exception{
        return roomDAO.readRoomQuestionByRoomIdAndUserId(roomId, userId);
    }

    @Override
    public ReadMemberListOutDTO readMemberListByRoomId(long roomId, long userId) throws Exception{
        List<MemberDTO> memberDTOS = roomDAO.readMemberListByRoomId(roomId, userId);
        if(memberDTOS == null) {
            throw new IllegalArgumentException();
        }else {
            ReadMemberListOutDTO readMemberListOutDTO = new ReadMemberListOutDTO(roomId, memberDTOS);
            return readMemberListOutDTO;
        }
    }
}
