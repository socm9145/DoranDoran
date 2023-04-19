package com.purple.hello.controller;

import com.purple.hello.dto.in.UpdateMoveAlarmInDTO;
import com.purple.hello.dto.in.UpdateRoomNameInDTO;
import com.purple.hello.dto.in.UpdateSafeAlarmInDTO;
import com.purple.hello.dto.in.UpdateUserNameInDTO;
import com.purple.hello.dto.out.UpdateMoveAlarmOutDTO;
import com.purple.hello.dto.out.UpdateRoomNameOutDTO;
import com.purple.hello.dto.out.UpdateSafeAlarmOutDTO;
import com.purple.hello.dto.out.UpdateUserNameOutDTO;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/option")
public class OptionController {
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
    OptionController(AlarmService alarmService, FeedService feedService, QuestionService questionService, RoomService roomService,
                      UserRoomService userRoomService, UserService userService){
        this.alarmService = alarmService;
        this.feedService = feedService;
        this.questionService = questionService;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
    }

    @ApiOperation(
            value = "그룹 이름 변경 API (v)"
            , notes = "해당 그룹의 이름을 변경해주는 API. 모든 이용자가 변경 가능하다.")
    @PutMapping("/room/name")
    public ResponseEntity<UpdateRoomNameOutDTO> updateRoomName(@RequestBody UpdateRoomNameInDTO updateRoomNameInDTO, HttpServletRequest request){
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        String updatedRoomName = userRoomService.updateRoomName(userId, updateRoomNameInDTO);
        UpdateRoomNameOutDTO updateRoomNameOutDTO = new UpdateRoomNameOutDTO(updatedRoomName);
        return ResponseEntity.status(HttpStatus.OK).body(updateRoomNameOutDTO);
    }

    @ApiOperation(
            value = "나의 이름 변경 API (v)"
            , notes = "해당 그룹에서 사용할 이용자의 이름을 변경해주는 API. 그룹방 별로 모든 이용자가 사용할 수 있다.")
    @PutMapping("/room/user")
    public ResponseEntity<UpdateUserNameOutDTO> updateUserName(@RequestBody UpdateUserNameInDTO updateUserNameInDTO, HttpServletRequest request){
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        String updatedUserName = userRoomService.updateUserName(userId, updateUserNameInDTO);
        UpdateUserNameOutDTO updateUserNameOutDTO = new UpdateUserNameOutDTO(updatedUserName);
        return ResponseEntity.status(HttpStatus.OK).body(updateUserNameOutDTO);
    }
    @ApiOperation(
            value = "무동작 감지 알람 변경 API (v)"
            , notes = "그룹 내 이용자의 무동작이 감지된 경우 이외 사용자에게 알람을 전하는 API")
    @PutMapping("/room/move-alarm")
    public ResponseEntity<UpdateMoveAlarmOutDTO> updateMoveAlarm(UpdateMoveAlarmInDTO updateMoveAlarmInDTO, HttpServletRequest request){
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        BoolAlarm moveAlarm = userRoomService.updateMoveAlarm(userId, updateMoveAlarmInDTO);
        UpdateMoveAlarmOutDTO updateMoveAlarmOutDTO = new UpdateMoveAlarmOutDTO(moveAlarm);
        return ResponseEntity.status(HttpStatus.OK).body(updateMoveAlarmOutDTO);
    }
    @ApiOperation(
            value = "안전 감지 알람 변경 API (v)",
            notes = "그룹 내 이용자의 위험이 감지된 경우 이외 사용자에게 알람을 전하는 API")
    @PutMapping("/room/safe-alarm")
    public ResponseEntity<?> updateSafeAlarm(@RequestBody UpdateSafeAlarmInDTO updateSafeAlarmInDTO, HttpServletRequest request){
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        updateSafeAlarmInDTO.setUserId(userId);
        UpdateSafeAlarmOutDTO updateSafeAlarmOutDTO = new UpdateSafeAlarmOutDTO(
                userRoomService.updateSafeAlarm(updateSafeAlarmInDTO)
        );
        return ResponseEntity.status(HttpStatus.OK).body(updateSafeAlarmOutDTO);
    }
}
