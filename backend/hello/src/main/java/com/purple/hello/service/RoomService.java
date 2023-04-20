package com.purple.hello.service;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomPasswordInDTO;
import com.purple.hello.dto.in.UpdateRoomCodeInDTO;
import com.purple.hello.dto.in.DeleteRoomInDTO;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.ReadRoomCodeOutDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.out.ReadUserRoomJoinOutDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import javax.transaction.Transactional;
import java.util.List;

@Service
public interface RoomService {
    List<ReadRoomOutDTO> readRoomByUserId(long userId);
    CreateRoomOutDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO);

    boolean comparePasswordByRoomCode(long roomId, String password);

    ReadUserRoomJoinOutDTO readUserRoomJoinByRoomCode(String roomCode);

    void updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO);
    ReadRoomCodeOutDTO readRoomCodeByRoomId(long roomId);

    void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO);
    @Transactional
    boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO);
}
