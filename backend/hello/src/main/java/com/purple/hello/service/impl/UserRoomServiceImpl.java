package com.purple.hello.service.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
import com.purple.hello.entity.UserRoom;
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
    public void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) {
        this.userRoomDAO.createUserRoom(createUserRoomInDTO, roomId);
    }

    @Override
    @Transactional
    public void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) {
        boolean isAlreadyUser = checkUserExist(createUserRoomJoinInDTO.getUserId(), createUserRoomJoinInDTO.getRoomId());
        if(isAlreadyUser){
            this.userRoomDAO.updateUserRoomRejoin(createUserRoomJoinInDTO);
        }else{
            this.userRoomDAO.createUserRoomJoin(createUserRoomJoinInDTO);
        }
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

    private boolean checkUserExist(long userId, long roomId) {
        UserRoom userRoom = userRoomDAO.readUserRoomByUserIdAndRoomId(userId, roomId);
        if(userRoom != null) {
            return true;
        }else {
            return false;
        }
    }
}
