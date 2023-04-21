package com.purple.hello.service.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoomServiceImpl implements UserRoomService {
    @Autowired
    private final UserRoomDAO userRoomDAO;
    public UserRoomServiceImpl(UserRoomDAO userRoomDAO){
        this.userRoomDAO = userRoomDAO;
    }
    @Override
    public CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) {
        return this.userRoomDAO.createUserRoom(createUserRoomInDTO, roomId);
    }

    @Override
    public void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) {
        this.userRoomDAO.createUserRoomJoin(createUserRoomJoinInDTO);
    }

    @Override
    @Transactional
    public String updateRoomName(long userId, UpdateRoomNameInDTO updateRoomNameInDTO) {
        return userRoomDAO.updateRoomName(userId, updateRoomNameInDTO);
    }

    @Override
    @Transactional
    public String updateUserName(long userId, UpdateUserNameInDTO updateUserNameInDTO) {
        return userRoomDAO.updateUserName(userId, updateUserNameInDTO);
    }

    @Override
    @Transactional
    public BoolAlarm updateMoveAlarm(long userId, UpdateMoveAlarmInDTO updateMoveAlarmInDTO) {
        return userRoomDAO.updateMoveAlarm(userId, updateMoveAlarmInDTO);
    }

    @Override
    @Transactional
    public BoolAlarm updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO) {
        return userRoomDAO.updateSafeAlarm(updateSafeAlarmInDTO);
    }

    @Override
    @Transactional
    public void deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO) {
        userRoomDAO.deleteUserRoom(deleteUserRoomInDTO);
    }
}
