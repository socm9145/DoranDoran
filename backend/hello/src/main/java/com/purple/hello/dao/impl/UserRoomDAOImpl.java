package com.purple.hello.dao.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.entity.Room;
import com.purple.hello.entity.User;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.repo.UserRepo;
import com.purple.hello.repo.UserRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserRoomDAOImpl implements UserRoomDAO {
    @Autowired
    private final UserRoomRepo userRoomRepo;
    @Autowired
    private final RoomRepo roomRepo;
    @Autowired
    private final UserRepo userRepo;

    public UserRoomDAOImpl(UserRoomRepo userRoomRepo, RoomRepo roomRepo, UserRepo userRepo){
        this.userRoomRepo = userRoomRepo;
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
    }
    @Override
    public void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) {
        Room room = this.roomRepo.getById(roomId);
        User user = this.userRepo.getById(createUserRoomInDTO.getUserId());
        System.out.println(user);

        UserRoom userRoom = UserRoom.builder()
                .userRoomRole(UserRoomRole.ROLE1)
                .userName(createUserRoomInDTO.getUserName())
                .roomName(createUserRoomInDTO.getRoomName())
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .dayAlarm(BoolAlarm.Y)
                .createAt(new Date())
                .room(room)
                .user(user)
                .build();

        room.getUserRoom().add(userRoom);
        user.getUserRoom().add(userRoom);

        //this.roomRepo.save(room);
        //this.userRepo.save(user);

        this.userRoomRepo.save(userRoom);
    }
}
