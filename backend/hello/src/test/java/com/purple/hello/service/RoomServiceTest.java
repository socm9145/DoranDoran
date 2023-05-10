package com.purple.hello.service;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dao.impl.HistoryDAOImpl;
import com.purple.hello.dao.impl.QuestionDAOImpl;
import com.purple.hello.dao.impl.RoomDAOImpl;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.service.impl.AwsS3ServiceImpl;
import com.purple.hello.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {
    static private RoomServiceImpl roomService;
    static private RoomDAOImpl roomDAO;
    static private HistoryDAOImpl historyDAO;
    static private QuestionDAOImpl questionDAO;
    static private QuestionRepo questionRepo;
    static private AwsS3ServiceImpl awsS3Service;
    @BeforeAll
    static void beforeAll(){
        roomService = Mockito.mock(RoomServiceImpl.class);
        roomDAO = Mockito.mock(RoomDAOImpl.class);
        historyDAO = Mockito.mock(HistoryDAOImpl.class);
        questionDAO = Mockito.mock(QuestionDAOImpl.class);
        questionRepo = Mockito.mock(QuestionRepo.class);
        awsS3Service = Mockito.mock(AwsS3ServiceImpl.class);
    }
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readRoomByUserId() {
        
    }

    @Test
    void createRoom() {
    }

    @Test
    void comparePasswordByRoomCode() {
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