package com.purple.hello.dao;

import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.CreateUserRoomJoinOutDTO;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;

import java.util.List;

public interface UserRoomDAO {
    CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId);
    CreateUserRoomJoinOutDTO createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    String updateRoomName(long userId, UpdateRoomNameInDTO updateRoomNameInDTO);
    String updateUserName(long userId, UpdateUserNameInDTO updateUserNameInDTO);
    BoolAlarm updateMoveAlarm(long userId, UpdateMoveAlarmInDTO updateMoveAlarmInDTO);
    BoolAlarm updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO);
    void deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO);
    UserRoom readUserRoomByUserIdAndRoomId(long userId, long roomId);
    void updateUserRoomRejoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    void updateUserRoomRoleByUserRoomId(long userRoomId, UserRoomRole userRoomRole);
    UserRoom readUserRoomByUserRoomId(long userRoomId);
    List<UserRoom> readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(long roomId, long userRoomId, int userRoomLimit);
}
