package com.purple.hello.dao;

import com.purple.hello.dto.in.*;
import com.purple.hello.enu.BoolAlarm;

public interface UserRoomDAO {
    void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId);
    void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    String updateRoomNameByRoomIdAndUserId(long userId, UpdateRoomNameInDTO updateRoomNameInDTO);
    String updateUserNameByRoomIdAndUserId(long userId, UpdateUserNameInDTO updateUserNameInDTO);
    BoolAlarm updateMoveAlarmByRoomIdAndUserId(long userId, UpdateMoveAlarmInDTO updateMoveAlarmInDTO);
}
