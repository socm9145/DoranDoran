package com.purple.hello.service;

import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomPasswordInDTO;
import com.purple.hello.dto.in.UpdateRoomCodeInDTO;
import com.purple.hello.dto.in.DeleteRoomInDTO;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.CreateRoomDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface RoomService {
    List<ReadRoomOutDTO> readRoomByUserId(long userId);
    CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) throws Exception;
    boolean comparePasswordByRoomCode(long roomId, String password);
    ReadUserRoomJoinOutDTO readUserRoomJoinByRoomCode(String roomCode);
    boolean updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO) throws Exception;
    ReadRoomCodeOutDTO readRoomCodeByRoomId(long roomId);
    void createQuestion();
    void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO);
    @Transactional
    boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) throws Exception;
    ReadQuestionOutDTO readQuestionByRoomId(long roomId) throws Exception;
    ReadRoomQuestionOutDTO readRoomQuestionByRoomIdAndUserId(long roomId, long userId);

    ReadMemberListOutDTO readMemberListByRoomId(long roomId, long userId);
}
