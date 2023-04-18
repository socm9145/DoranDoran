package com.purple.hello.service.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
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
    public void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) {
        this.userRoomDAO.createUserRoomJoin(createUserRoomJoinInDTO);
    }
    /**
     * userRoom의 roomName을 변경시키는 dao를 호출하는 함수
     * */
    @Override
    @Transactional
    public String updateRoomNameByRoomIdAndUserId(long userId, UpdateRoomNameInDTO updateRoomNameInDTO) {
        return userRoomDAO.updateRoomNameByRoomIdAndUserId(userId, updateRoomNameInDTO);
    }

    /**
     * userRoom의 userName을 변경시키는 dao를 호출하는 함수
     * */
    @Override
    @Transactional
    public String updateUserNameByRoomIdAndUserId(long userId, UpdateUserNameInDTO updateUserNameInDTO) {
        return userRoomDAO.updateUserNameByRoomIdAndUserId(userId, updateUserNameInDTO);
    }

    /**
     * userRoom의 moveAlarm을 변경시키는 dao를 호출하는 함수
     * */
    @Override
    @Transactional
    public BoolAlarm updateMoveAlarmByRoomIdAndUserId(long userId, UpdateMoveAlarmInDTO updateMoveAlarmInDTO) {
        return userRoomDAO.updateMoveAlarmByRoomIdAndUserId(userId, updateMoveAlarmInDTO);
    }
}
