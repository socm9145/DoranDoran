package com.purple.hello.service.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.out.ReadUserRoomJoinOutDTO;
import com.purple.hello.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private final RoomDAO roomDAO;

    RoomServiceImpl(RoomDAO roomDAO){
        this.roomDAO = roomDAO;
    }
    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) {
        return this.roomDAO.readRoomByUserId(userId);
    }

    @Override
    public CreateRoomOutDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) {
        return this.roomDAO.createRoom(createUserRoomInDTO);
    }

    @Override
    public boolean comparePasswordByRoomCode(long roomId, String password) {
        return this.roomDAO.comparePasswordByRoomCode(roomId, password);
    }

    @Override
    public ReadUserRoomJoinOutDTO readUserRoomJoin(String roomCode) {
        return this.roomDAO.readUserRoomJoin(roomCode);
    }
}
