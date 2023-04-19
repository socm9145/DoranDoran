package com.purple.hello.service;

import com.purple.hello.dto.in.*;
import com.purple.hello.enu.BoolAlarm;
import org.springframework.stereotype.Service;

@Service
public interface UserRoomService {
    void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId);
    void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    String updateRoomNameByRoomIdAndUserId(long userId, UpdateRoomNameInDTO updateRoomNameInDTO);
    String updateUserNameByRoomIdAndUserId(long userId, UpdateUserNameInDTO updateUserNameInDTO);
    BoolAlarm updateMoveAlarmByRoomIdAndUserId(long userId, UpdateMoveAlarmInDTO updateMoveAlarmInDTO);

    BoolAlarm updateSafeAlarmByRoomIdAndUserId(UpdateSafeAlarmInDTO updateSafeAlarmInDTO);
}
