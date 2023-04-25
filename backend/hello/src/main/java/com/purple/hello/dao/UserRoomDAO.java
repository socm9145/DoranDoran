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
    boolean updateRoomName(UpdateRoomNameInDTO updateRoomNameInDTO);
    boolean updateUserName(UpdateUserNameInDTO updateUserNameInDTO);
    boolean updateMoveAlarm(UpdateMoveAlarmInDTO updateMoveAlarmInDTO);
    boolean updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO);
    void deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO);
    UserRoom readUserRoomByUserIdAndRoomId(long userId, long roomId);
    CreateUserRoomJoinOutDTO updateUserRoomRejoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    void updateUserRoomRoleByUserRoomId(long userRoomId, UserRoomRole userRoomRole);
    UserRoom readUserRoomByUserRoomId(long userRoomId);
    List<UserRoom> readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(long roomId, long userRoomId, int userRoomLimit);
}
