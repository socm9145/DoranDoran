package com.purple.hello.controller;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/room")
public class RoomController {
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
}
