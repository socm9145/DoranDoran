package com.purple.hello.service;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dao.impl.HistoryDAOImpl;
import com.purple.hello.dao.impl.QuestionDAOImpl;
import com.purple.hello.dao.impl.RoomDAOImpl;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.tool.MemberDTO;
import com.purple.hello.encoder.PasswordEncoder;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.service.impl.AwsS3ServiceImpl;
import com.purple.hello.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class RoomServiceTest {
    static private RoomServiceImpl roomService;
    static private RoomDAOImpl roomDAO;
    static private HistoryDAOImpl historyDAO;
    static private QuestionDAOImpl questionDAO;
    static private QuestionRepo questionRepo;
    static private AwsS3ServiceImpl awsS3Service;
    static private PasswordEncoder passwordEncoder;
    @BeforeAll
    static void beforeAll(){
        roomService = Mockito.mock(RoomServiceImpl.class);
        roomDAO = Mockito.mock(RoomDAOImpl.class);
        historyDAO = Mockito.mock(HistoryDAOImpl.class);
        questionDAO = Mockito.mock(QuestionDAOImpl.class);
        questionRepo = Mockito.mock(QuestionRepo.class);
        awsS3Service = Mockito.mock(AwsS3ServiceImpl.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
    }
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readRoomByUserId() throws Exception{
        List<ReadRoomOutDTO> initReadRoomOutDTOs = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            initReadRoomOutDTOs.add(ReadRoomOutDTO.builder()
                            .userRoomId(1)
                            .roomId(i)
                            .roomName("test_roomName")
                            .members(new ArrayList<>())
                        .build());
        }

        // given
        Mockito.when(roomDAO.readRoomByUserId(any(Long.class)))
                .thenReturn(initReadRoomOutDTOs);

        // when
        List<ReadRoomOutDTO> whenReadRoomOutDTOs = roomService.readRoomByUserId(1);

        // then
        for (int i = 0; i < 4; i++){
            assertEquals(initReadRoomOutDTOs.get(i).getRoomId(), whenReadRoomOutDTOs.get(i).getRoomId());
            assertEquals(initReadRoomOutDTOs.get(i).getUserRoomId(), whenReadRoomOutDTOs.get(i).getUserRoomId());
            assertEquals(initReadRoomOutDTOs.get(i).getRoomName(), whenReadRoomOutDTOs.get(i).getRoomName());
            assertEquals(initReadRoomOutDTOs.get(i).getMembers().size(), whenReadRoomOutDTOs.get(i).getMembers().size());

            int size = initReadRoomOutDTOs.get(i).getMembers().size();

            for (int j = 0; j < size; j++){
                assertEquals(initReadRoomOutDTOs.get(i).getMembers().get(j)
                        , whenReadRoomOutDTOs.get(i).getMembers().get(j));
            }
        }
    }

    @Test
    void readRoomByUserId_InvalidService() throws Exception{
        // given
        Mockito.when(roomDAO.readRoomByUserId(any(Long.class)))
                .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class,() -> {
            List<ReadRoomOutDTO> whenReadRoomOutDTOs = roomService.readRoomByUserId(1);
        });
    }


    @Test
    void createRoom() throws Exception{
        // init
        CreateRoomDTO initCreateRoomDTO = CreateRoomDTO.builder()
                .roomId(1)
                .build();

        // given
        Mockito.when(roomDAO.createRoom(any(CreateUserRoomInDTO.class)))
                .thenReturn(CreateRoomDTO.builder()
                        .roomId(initCreateRoomDTO.getRoomId())
                        .build());

        // when
        CreateRoomDTO whenCreateRoomDTO = roomService.createRoom(CreateUserRoomInDTO.builder()
                        .roomPassword("test_roomPassword")
                        .roomQuestion("test_roomQuestion")
                        .userId(1)
                        .roomQuestion("test_roomQuestion")
                        .userName("test_userName")
                .build());

        // then
        assertEquals(whenCreateRoomDTO.getRoomId(), initCreateRoomDTO.getRoomId());
    }

    void createRoom_invalidService() throws Exception{
        // given
        Mockito.when(roomDAO.createRoom(any(CreateUserRoomInDTO.class)))
                .thenThrow(new IllegalArgumentException());

        // when-then
        assertThrows(IllegalArgumentException.class, () ->{
            CreateRoomDTO createRoomDTO = roomService.createRoom(CreateUserRoomInDTO.builder()
                    .roomPassword("test_roomPassword")
                    .roomQuestion("test_roomQuestion")
                    .userId(1)
                    .roomQuestion("test_roomQuestion")
                    .userName("test_userName")
                    .build());
        });
    }

    @Test
    void comparePasswordByRoomCode() throws Exception {
        // init
        long initRoomId = 1;
        String initStoredPassword = "test_StoredPassword";
        String initPassword = "test_Password";

        // given
        Mockito.when(roomDAO.comparePasswordByRoomCode(initRoomId))
                .thenReturn(initStoredPassword);

        // when
        boolean whenResult = roomService.comparePasswordByRoomCode(initRoomId, initStoredPassword);

        // then
        assertTrue(whenResult);
    }

    @Test
    void comparePasswordByRoomCode_IsWrong() throws Exception {
        // init
        long initRoomId = 1;
        String initStoredPassword = "test_StoredPassword";

        // given
        Mockito.when(roomDAO.comparePasswordByRoomCode(initRoomId))
                .thenReturn(initStoredPassword);

        Mockito.when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        // when
        boolean whenResult = roomService.comparePasswordByRoomCode(initRoomId, initStoredPassword);

        // then
        assertFalse(whenResult);
    }

    @Test
    void comparePasswordByRoomCode_InavlidService() throws Exception {
        // init
        long initRoomId = 1;
        String initStoredPassword = "test_StoredPassword";

        // given
        Mockito.when(roomDAO.comparePasswordByRoomCode(initRoomId))
                        .thenThrow(new IllegalArgumentException());

        // when - then
        assertThrows(IllegalArgumentException.class, () -> {
            roomService.comparePasswordByRoomCode(initRoomId, initStoredPassword);
        });
    }
    @Test
    void readUserRoomJoinByRoomCode() {

    }

    @Test
    void updateRoomPassword() {
    }

    @Test
    void readRoomCodeByRoomId() {
    }

    @Test
    void updateRoomCodeByRoomId() {
    }

    @Test
    void deleteRoom() {
    }

    @Test
    void readQuestionByRoomId() {
    }

    @Test
    void readRoomQuestionByRoomIdAndUserId() {
    }

    @Test
    void readMemberListByRoomId() {
    }
}