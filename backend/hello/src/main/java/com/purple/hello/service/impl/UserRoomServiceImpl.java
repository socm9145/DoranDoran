package com.purple.hello.service.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoomServiceImpl implements UserRoomService {
    @Autowired
    private final UserRoomDAO userRoomDAO;
    public UserRoomServiceImpl(UserRoomDAO userRoomDAO){
        this.userRoomDAO = userRoomDAO;
    }
    @Override
    public void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) {
        this.userRoomDAO.createUserRoom(createUserRoomInDTO, roomId);
    }
}
