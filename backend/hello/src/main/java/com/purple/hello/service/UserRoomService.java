package com.purple.hello.service;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.CreateUserRoomJoinInDTO;
import com.purple.hello.dto.in.UpdateRoomNameInDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserRoomService {
    void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId);
    void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO);
    String updateRoomNameByRoomIdAndUserId(long userId, UpdateRoomNameInDTO updateRoomNameInDTO);
}
