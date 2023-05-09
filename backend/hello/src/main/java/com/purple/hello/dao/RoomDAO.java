package com.purple.hello.dao;

import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.tool.HistoryMinMaxDTO;
import com.purple.hello.dto.tool.HistoryTypeDTO;
import com.purple.hello.entity.Room;
import com.purple.hello.dto.tool.MemberDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RoomDAO {
    List<ReadRoomOutDTO> readRoomByUserId(long userId);
    CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO)throws Exception;
    String comparePasswordByRoomCode(long roomId);
    ReadUserRoomJoinOutDTO readUserRoomJoinByRoomCode(String roomCode);
    boolean updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO)  throws Exception;
    String readRoomCodeByRoomId(long roomId);
    void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO);
    boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) throws Exception;
    LocalDate getCreatedAtByRoomId(long roomId);
    List<Room> getRoom();
    Map<Long, List<HistoryTypeDTO>> getHistoryTypeCount(List<Long> roomListIdx);
    Map<Long, List<HistoryTypeDTO>> getHistoryTypeFeedCount(List<Long> roomListIdx);
    Map<Long, List<HistoryMinMaxDTO>> getHistoryMinMax(List<Long> roomListIdx);
    ReadRoomQuestionOutDTO readRoomQuestionByRoomIdAndUserId(long roomId, long userId);
    List<MemberDTO> readMemberListByRoomId(long roomId, long userId);
    Integer readBeginTimeByRoomId(long roomId);
}
