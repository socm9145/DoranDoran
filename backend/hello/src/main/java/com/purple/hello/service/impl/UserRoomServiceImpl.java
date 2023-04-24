package com.purple.hello.service.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoomServiceImpl implements UserRoomService {
    @Autowired
    private final UserRoomDAO userRoomDAO;
    @Autowired
    private final RoomDAO roomDAO;
    public UserRoomServiceImpl(UserRoomDAO userRoomDAO, RoomDAO roomDAO){
        this.userRoomDAO = userRoomDAO;
        this.roomDAO = roomDAO;
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
    public boolean deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO) {
        final int USER_ROOM_LIMIT = 1;
        UserRoom userRoom = userRoomDAO.readUserRoomByUserRoomId(deleteUserRoomInDTO.getUserRoomId());
        List<UserRoom> userRooms = userRoomDAO.readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(userRoom.getRoom().getRoomId(), userRoom.getUserRoomId(), USER_ROOM_LIMIT);
        if(userRooms.size() > 0) {
            userRoomDAO.updateUserRoomRoleByUserRoomId(deleteUserRoomInDTO.getUserRoomId(), UserRoomRole.ROLE3);
            userRoom = userRooms.get(0);
            userRoomDAO.updateUserRoomRoleByUserRoomId(userRoom.getUserRoomId(), UserRoomRole.ROLE1);
        }else {
            DeleteRoomInDTO deleteRoomInDTO = new DeleteRoomInDTO(userRoom.getRoom().getRoomId(), userRoom.getUser().getUserId());
            if (roomDAO.deleteRoom(deleteRoomInDTO)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
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
