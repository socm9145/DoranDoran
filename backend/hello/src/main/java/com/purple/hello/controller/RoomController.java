package com.purple.hello.controller;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
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
    @PostMapping("/create")
    public ResponseEntity<Void> createRoom(@RequestBody CreateUserRoomInDTO createUserRoomInDTO){

        return new ResponseEntity(HttpStatus.OK);
    }
}
