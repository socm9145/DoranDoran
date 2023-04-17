package com.purple.hello.dao.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomDAOImpl implements RoomDAO {
    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) {
        return null;
    }
}
