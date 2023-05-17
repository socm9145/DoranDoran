package com.purple.hello.service.impl;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.dto.tool.DeviceWithQuestionDTO;
import com.purple.hello.dto.tool.CommonNotificationDTO;
import com.purple.hello.dto.tool.NotificationDTO;
import com.purple.hello.entity.History;
import com.purple.hello.entity.Question;
import com.purple.hello.entity.Room;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.service.HistoryService;
import com.purple.hello.util.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final RoomDAO roomDAO;
    private final HistoryDAO historyDAO;

    public HistoryServiceImpl(RoomDAO roomDAO, HistoryDAO historyDAO) {
        this.roomDAO = roomDAO;
        this.historyDAO = historyDAO;
    }

    @Override
    public ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException, NullPointerException {
        if(date == null)
            throw new IllegalArgumentException();

        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = DateUtils.addHours(new Date(), 9);
            date = DateUtils.addHours(date, 9);
            if(currentDate.compareTo(date) < 0)
                throw new IllegalArgumentException();

            if(simpleDateFormat.format(currentDate).equals(simpleDateFormat.format(date))) {
                int beginTime = roomDAO.readBeginTimeByRoomId(roomId);
                int currentHour = LocalDateTime.now().plusHours(9).getHour();

                if(currentHour < beginTime)
                    throw new IllegalArgumentException();
            }
            return historyDAO.readQuestionByRoomIdAndDate(roomId, date);
        }
    }

    @Override
    public List<CommonNotificationDTO> createNewQuestionNotificationsByBeginTime(int beginTime) throws Exception {
        List<DeviceWithQuestionDTO> deviceWithQuestionDTOS = historyDAO.readDevicesWithDailyQuestionByBeginTime(beginTime);
        String sentence = "의 오늘의 질문이 생성되었습니다.";
        return parseNotificationList(deviceWithQuestionDTOS, sentence);
    }

    @Override
    public List<CommonNotificationDTO> createRemindQuestionNotificationsByBeginTime(int beginTime) throws Exception {
        List<DeviceWithQuestionDTO> deviceWithQuestionDTOS = historyDAO.readDevicesNotUploadedByBeginTime(beginTime);
        String sentence = "의 오늘의 질문 마감 1시간 전입니다.";
        return parseNotificationList(deviceWithQuestionDTOS, sentence);
    }
    
    @Override
    public void createFirstHistory(Long roomId) {
        historyDAO.createFirstHistory(roomId);
    }

    private List<CommonNotificationDTO> parseNotificationList(List<DeviceWithQuestionDTO> deviceWithQuestionDTOS, String sentence) {
        Map<Long, CommonNotificationDTO> roomNotificationMap = new HashMap<>();
        for(DeviceWithQuestionDTO deviceWithQuestionDTO : deviceWithQuestionDTOS) {
            if(!roomNotificationMap.containsKey(deviceWithQuestionDTO.getRoomId())) {
                List<String> tokenList = new ArrayList<>();
                tokenList.add(deviceWithQuestionDTO.getDeviceToken());
                String title = deviceWithQuestionDTO.getRoomName() + sentence;
                String content = deviceWithQuestionDTO.getContent();
                roomNotificationMap.put(deviceWithQuestionDTO.getRoomId(), CommonNotificationDTO.builder()
                        .tokenList(tokenList)
                        .title(title)
                        .content(content)
                        .build());
            }else {
                long roomId = deviceWithQuestionDTO.getRoomId();
                CommonNotificationDTO commonNotificationDTO = roomNotificationMap.get(roomId);
                List<String> tokenList = commonNotificationDTO.getTokenList();
                tokenList.add(deviceWithQuestionDTO.getDeviceToken());
            }
        }
        List<CommonNotificationDTO> commonNotificationDTOS = new ArrayList<>();
        for(CommonNotificationDTO commonNotificationDTO : roomNotificationMap.values()) {
            commonNotificationDTOS.add(commonNotificationDTO);
        }
        return commonNotificationDTOS;
    }
}
