package com.purple.hello.controller;

import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.CreateUserRoomJoinInDTO;
import com.purple.hello.dto.in.UpdateRoomCodeInDTO;
import com.purple.hello.dto.out.*;
import com.purple.hello.room.RoomCode;
import com.purple.hello.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomCode roomCode;
    @Autowired
    private final AlarmService alarmService;
    @Autowired
    private final FeedService feedService;
    @Autowired
    private final QuestionService questionService;
    @Autowired
    private final RoomService roomService;
    @Autowired
    private final UserRoomService userRoomService;
    @Autowired
    private final UserService userService;
    RoomController(AlarmService alarmService, FeedService feedService, QuestionService questionService, RoomService roomService,
                     UserRoomService userRoomService, UserService userService){
        this.alarmService = alarmService;
        this.feedService = feedService;
        this.questionService = questionService;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
    }

    @ApiOperation(
            value = "그룹방 생성 API (v)"
            , notes = "관리자가 그룹방을 생성할 경우 그룹방에 맞는 정보를 저장해주는 API")
    @PostMapping("/create")
    public ResponseEntity<Void> createRoom(@RequestBody CreateUserRoomInDTO createUserRoomInDTO){
        // room 객체 생성
        CreateRoomOutDTO createRoomOutDTO = this.roomService.createRoom(createUserRoomInDTO);
        // userRoom 객체 생성
        this.userRoomService.createUserRoom(createUserRoomInDTO, createRoomOutDTO.getRoomId());

        return new ResponseEntity(HttpStatus.OK);
    }
    @ApiOperation(
            value = "그룹방 입장 정보 출력 API",
            notes = "그룹방 코드를 입력할 경우 다이얼로그에 사용할 정보를 출력해주는 API")
    @GetMapping("/join")
    public ResponseEntity<ReadUserRoomJoinOutDTO> readUserRoomJoinByRoomCode(@RequestParam("code") String roomCode){
        ReadUserRoomJoinOutDTO readUserRoomJoinOutDTO = this.roomService.readUserRoomJoinByRoomCode(roomCode);

        return ResponseEntity.status(HttpStatus.OK).body(readUserRoomJoinOutDTO);
    }

    @ApiOperation(
            value = "그룹방 정보 추가 API (v)",
            notes = "이용자가 그룹방에 입장할 경우 그룹방 정보를 수정 및 추가해주는 API")
    @PostMapping("/join")
    public ResponseEntity<Boolean> createUserRoomJoin(@RequestBody CreateUserRoomJoinInDTO createUserRoomJoinInDTO){
        // 비밀번호 여부 확인
        // 여기까지 해야 하나..
        boolean comparePasswordByRoomCode = this.roomService.comparePasswordByRoomCode(createUserRoomJoinInDTO.getRoomId(),
                createUserRoomJoinInDTO.getRoomPassword());

        if (!comparePasswordByRoomCode)
            return ResponseEntity.status(HttpStatus.OK).body(false);

        // 그룹방 정보 추가
        this.userRoomService.createUserRoomJoin(createUserRoomJoinInDTO);

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @ApiOperation(value = "사진 제출 여부 확인 API",
            notes = "이용자별 사진 제출 여부를 확인해주는 API")
    @GetMapping("/photo")
    public ResponseEntity<List<CompareFeedByRoomIdOutDTO>> compareFeedByUserIdAndRoomId(@RequestParam("roomId") long roomId,
                                                                                        @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdOutDTOS
                = this.feedService.compareFeedByRoomIdAndDate(roomId, date);
        return ResponseEntity.status(HttpStatus.OK).body(compareFeedByRoomIdOutDTOS);
    }
    @ApiOperation(value = "피드 생성 API",
                  notes = "이용자가 피드를 올릴 경우 피드 정보를 저장해주는 API")
    @PostMapping("/feed")
    public ResponseEntity<CreateFeedOutDTO> createFeed(CreateFeedInDTO createFeedInDTO){
        CreateFeedOutDTO result = this.feedService.createFeed(createFeedInDTO);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @ApiOperation(value = "초대 링크 생성 API",
                    notes = "해당 그룹방 초대 링크를 출력")
    @GetMapping("/code")
    public ResponseEntity<ReadRoomCodeOutDTO> readRoomCodeByRoomId(@RequestParam long roomId){
        // 방의 초대 링크를 가져온다
        String priUrl = roomService.readRoomCodeByRoomId(roomId);
        Instant currentTime = Instant.now();
        ReadRoomCodeOutDTO readRoomCodeOutDTO = new ReadRoomCodeOutDTO();
        UpdateRoomCodeInDTO updateRoomCodeInDTO = new UpdateRoomCodeInDTO();
        updateRoomCodeInDTO.setRoomId(roomId);
        if(priUrl != null){
            String createdTimeStr = roomCode.getTime(priUrl);
            Instant createdTime = Instant.parse(createdTimeStr);
            Duration diff = Duration.between(createdTime, currentTime);
            // 가져온 초대 링크와 현재 시간을 비교해서 기간이 끝났으면 재생성
            if(diff.compareTo(Duration.ofDays(1)) > 0){
                // 만료 되었음
                String newUrl = roomCode.makeUrl(roomId, currentTime);
                readRoomCodeOutDTO.setRoomCode(newUrl);
                // 생성된 디퍼드 딥링크를 디비에 다시 저장
                updateRoomCodeInDTO.setRoomCode(newUrl);
                roomService.updateRoomCodeByRoomId(updateRoomCodeInDTO);
            }else{
                readRoomCodeOutDTO.setRoomCode(priUrl);
            }
            return ResponseEntity.status(HttpStatus.OK).body(readRoomCodeOutDTO);
        }
        String newUrl = roomCode.makeUrl(roomId, currentTime);
        updateRoomCodeInDTO.setRoomCode(newUrl);
        roomService.updateRoomCodeByRoomId(updateRoomCodeInDTO);
        readRoomCodeOutDTO.setRoomCode(newUrl);
        return ResponseEntity.status(HttpStatus.OK).body(readRoomCodeOutDTO);
    }

}
