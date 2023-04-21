package com.purple.hello.dao;

import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.enu.BoolAlarm;

public interface UserRoomDAO {
    CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId);
    void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    String updateRoomName(long userId, UpdateRoomNameInDTO updateRoomNameInDTO);
    String updateUserName(long userId, UpdateUserNameInDTO updateUserNameInDTO);
    BoolAlarm updateMoveAlarm(long userId, UpdateMoveAlarmInDTO updateMoveAlarmInDTO);
    BoolAlarm updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO);
    void deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO);
}
