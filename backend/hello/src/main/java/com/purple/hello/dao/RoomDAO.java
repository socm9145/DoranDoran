package com.purple.hello.dao;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomPasswordInDTO;
import com.purple.hello.dto.in.UpdateRoomCodeInDTO;
import com.purple.hello.dto.in.DeleteRoomInDTO;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.out.ReadUserRoomJoinOutDTO;

import java.util.List;

public interface RoomDAO {
    List<ReadRoomOutDTO> readRoomByUserId(long userId);
    CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO);
    String comparePasswordByRoomCode(long roomId);
    ReadUserRoomJoinOutDTO readUserRoomJoinByRoomCode(String roomCode);
    void updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO);
    String readRoomCodeByRoomId(long roomId);
    void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO);
    boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO);
}
