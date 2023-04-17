package com.purple.hello.dao.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.UserRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserRoomDAOimpl implements UserRoomDAO {
    @Autowired
    private final UserRoomRepo userRoomRepo;
    public UserRoomDAOimpl(UserRoomRepo userRoomRepo){
        this.userRoomRepo = userRoomRepo;
    }
    @Override
    public void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO) {
        UserRoom userRoom = UserRoom.builder()
                .userRoomRole(UserRoomRole.ROLE1)
                .userName(createUserRoomInDTO.getUserName())
                .roomName(createUserRoomInDTO.getRoomName())
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .dayAlarm(BoolAlarm.Y)
                .createAt(new Date())
                .build();

        this.userRoomRepo.save(userRoom);
    }
}
