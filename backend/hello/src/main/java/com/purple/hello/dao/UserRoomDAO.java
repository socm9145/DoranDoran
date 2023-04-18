package com.purple.hello.dao;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomNameInDTO;
import com.purple.hello.dto.in.UpdateUserNameInDTO;

public interface UserRoomDAO {
    void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId);
    String updateRoomNameByRoomIdAndUserId(long userId, UpdateRoomNameInDTO updateRoomNameInDTO);
    String updateUserNameByRoomIdAndUserId(long userId, UpdateUserNameInDTO updateUserNameInDTO);
}
