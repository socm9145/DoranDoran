package com.purple.hello.service.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
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
}
