package com.purple.hello.service;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    List<ReadRoomOutDTO> readRoomByUserId(long userId);

    void createRoom(CreateUserRoomInDTO createUserRoomInDTO);
}
