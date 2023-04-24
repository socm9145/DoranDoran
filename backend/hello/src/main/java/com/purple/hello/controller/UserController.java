package com.purple.hello.controller;

import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
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
    UserController(AlarmService alarmService, FeedService feedService, QuestionService questionService, RoomService roomService,
                   UserRoomService userRoomService, UserService userService){
        this.alarmService = alarmService;
        this.feedService = feedService;
        this.questionService = questionService;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
    }

    @ApiOperation(
            value = "그룹방 출력 API (v) vv"
            , notes = "그룹 화면에서 사용자에게 맞는 그룹방을 출력해주는 API")
    @GetMapping("/")
    public ResponseEntity<List<ReadRoomOutDTO>> readRoomByUserId(@RequestParam("userId") long userId){
        List<ReadRoomOutDTO> readRoomOutDTOs = roomService.readRoomByUserId(userId);

        if (readRoomOutDTOs == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(readRoomOutDTOs);
    }

    @GetMapping("/filter")
    public String filterTest(HttpServletRequest request){
        long userId = (long) request.getAttribute("userId");

        return "User ID: " + userId;
    }


}
