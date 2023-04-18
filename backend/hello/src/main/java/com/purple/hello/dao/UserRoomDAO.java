package com.purple.hello.dao;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomNameInDTO;
import com.purple.hello.dto.in.CreateUserRoomJoinInDTO;

public interface UserRoomDAO {
    void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId);
    void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    String updateRoomNameByRoomIdAndUserId(long userId, UpdateRoomNameInDTO updateRoomNameInDTO);
}
