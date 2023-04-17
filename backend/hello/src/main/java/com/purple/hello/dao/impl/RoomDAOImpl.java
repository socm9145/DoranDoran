package com.purple.hello.dao.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.entity.Room;
import com.purple.hello.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RoomDAOImpl implements RoomDAO {
    @Autowired
    private final RoomRepo roomRepo;
    public RoomDAOImpl(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) {
        return null;
    }

    @Override
    public void createRoom(CreateUserRoomInDTO createUserRoomInDTO) {
        Room room = Room.builder()
                .beginTime(0)
                .createAt(new Date())
                .roomCode("room-code")
                .roomQuestion(createUserRoomInDTO.getRoomQuestion())
                .roomPassword(createUserRoomInDTO.getRoomPassword())
                .build();

        roomRepo.save(room);
    }
}
