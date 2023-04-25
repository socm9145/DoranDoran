package com.purple.hello.service;

import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.CreateUserRoomJoinOutDTO;
import com.purple.hello.enu.BoolAlarm;
import org.springframework.stereotype.Service;

@Service
public interface UserRoomService {
    CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId);
    CreateUserRoomJoinOutDTO createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    boolean updateRoomName(UpdateRoomNameInDTO updateRoomNameInDTO);
    boolean updateUserName(UpdateUserNameInDTO updateUserNameInDTO);
    boolean updateMoveAlarm(UpdateMoveAlarmInDTO updateMoveAlarmInDTO);
    boolean updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO);
    boolean deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO);
}
