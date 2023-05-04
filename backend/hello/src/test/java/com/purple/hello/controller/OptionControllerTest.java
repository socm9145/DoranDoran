package com.purple.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomNameInDTO;
import com.purple.hello.entity.UserRoom;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.filters.JwtFilter;
import com.purple.hello.repo.UserRoomRepo;
import com.purple.hello.service.impl.*;
import com.purple.hello.test.TestConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Test;


import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableConfigurationProperties
@AutoConfigureMockMvc
@WebMvcTest(OptionController.class)
public class OptionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    AlarmServiceImpl alarmService;
    @MockBean
    FeedServiceImpl feedService;
    @MockBean
    QuestionServiceImpl questionService;
    @MockBean
    RoomServiceImpl roomService;
    @MockBean
    UserRoomServiceImpl userRoomService;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    UserRoomRepo userRoomRepo;

    @BeforeAll
    void beforeAll() throws Exception{

    }

    @AfterEach
    void tearDown() {
    }

    @BeforeEach
    public void setUp(){
    }

    // TEST
    @Test
    void test_UpdateRoomName_InValidUserId_ThrowException() throws Exception {
        /*
        method: userRoomService.updateRoomName()
        situation: 존재하지 않은 userId를 입력하는 경우
        */

        // init
        UpdateRoomNameInDTO updateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(-1)
                .userRoomId(1)
                .roomName("roomName")
                .build();

        // given
        given(userRoomService.updateRoomName(updateRoomNameInDTO)).willReturn(false);

        // when
        String content = objectMapper.writeValueAsString(updateRoomNameInDTO);

        MvcResult mockResult = mockMvc.perform(put("/room/name")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateRoomNameInDTO.getUserId()))
                        .andReturn();

        // then
        int mockResultStatus = mockResult.getResponse().getStatus();
        String mockResultContent = mockResult.getResponse().getContentAsString();

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), mockResultStatus);
        Assertions.assertEquals("FAILED", mockResultContent);
    }

    @Test
    void test_UpdateRoomName_InvalidUserRoomName_ThrowException() throws Exception {
        /*
        method: userRoomService.updateRoomName()
        situation: 존재하지 않은 userRoomId를 입력하는 경우
        */

        // init
        UpdateRoomNameInDTO updateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(-1)
                .roomName("roomName")
                .build();

        // given
        given(userRoomService.updateRoomName(updateRoomNameInDTO)).willReturn(false);

        // when
        String content = objectMapper.writeValueAsString(updateRoomNameInDTO);

        MvcResult mockResult = mockMvc.perform(put("/room/name")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateRoomNameInDTO.getUserId()))
                .andReturn();

        // then
        int mockResultStatus = mockResult.getResponse().getStatus();
        String mockResultContent = mockResult.getResponse().getContentAsString();

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), mockResultStatus);
        Assertions.assertEquals("FAILED", mockResultContent);
    }

    @Test
    void test_UpdateRoomName_InsertNullInRoomName_ThrowException() throws Exception {
        /*
        method: userRoomService.updateRoomName()
        situation: 변경할 roomName을 NULL로 입력하는 경우
        */

        // init
        UpdateRoomNameInDTO updateRoomNameInDTO = UpdateRoomNameInDTO.builder()
                .userId(1)
                .userRoomId(1)
                .roomName(null)
                .build();

        String content = objectMapper.writeValueAsString(updateRoomNameInDTO);

        // given
        given(userRoomService.updateRoomName(any()))
                .willThrow(new PersistenceException());

        // when
        mockMvc.perform(put("/room/name")
                        .characterEncoding("UTF-8")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .requestAttr("userId", updateRoomNameInDTO.getUserId()))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof PersistenceException))
                .andDo(print())
                .andReturn();
    }


    @Test
    @DisplayName("존재하지 않는 userId를 주는 경우")
    void test_UpdateUserName_InValidUserId_ThrowException() throws Exception {


    }

    @Test
    @DisplayName("존재하지 않는 userRoomId를 주는 경우")
    void test_UpdateUserName_InValidUserRoomId_ThrowException() throws Exception {

    }



    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_UpdateRoomPassword_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 roomId를 입력하는 경우")
    void test_UpdateRoomPassword_InValidRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomQuestion에 NULL을 입력하는 경우")
    void test_UpdateRoomPassword_InsertNullInRoomQuestion_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomPasswrod에 NULL을 입력하는 경우")
    void test_UpdateRoomPassword_InsertNullInRoomPassword_ThrowException() throws Exception {

    }



    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_UpdateMoveAlarm_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 userRoomId를 입력하는 경우")
    void test_UpdateMoveAlarm_InValidUserRoomId_ThrowException() throws Exception {

    }



    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_UpdateSafeAlarm_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 userRoomId를 입력하는 경우")
    void test_UpdateSafeAlarm_InValidUserRoomId_ThrowException() throws Exception {

    }



    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_DeleteRoom_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 userRoomId를 입력하는 경우")
    void test_DeleteRoom_InValidUserRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("userId가 관리자가 아닌 경우")
    void test_DeleteRoom_InValidRole_ThrowException() throws Exception {

    }



    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_DeleteUserRoom_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 userRoomId를 입력하는 경우")
    void test_DeleteUserRoom_InValidUserRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("관리자가 그룹을 탈퇴하는 경우")
    void test_DeleteUserRoom_ExitAdmin_ChangeAdmin() throws Exception {

    }

    @Test
    @DisplayName("마지막 인원이 그룹을 탈퇴하는 경우")
    void test_DeleteUserRoom_ExitLastUser_DeleteRoom() throws Exception {

    }

    @Test
    @DisplayName("roomName에 NULL을 입력하는 경우")
    void test_CreateRoom_InsertNullInRoomName_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("userName에 NULL을 입력하는 경우")
    void test_CreateRoom_InsertNullInUserName_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomQuestion에 NULL을 입력하는 경우")
    void test_CreateRoom_InsertNullInRoomQuestion_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomPassword에 NULL을 입력하는 경우")
    void test_CreateRoom_InsertNullInRoomPassword_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomName을 입력하지 않는 경우")
    void test_CreateRoom_InsertNothingInRoomName_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("userName을 입력하지 않는 경우")
    void test_CreateRoom_InsertNothingInUserName_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomQuestion을 입력하지 않는 경우")
    void test_CreateRoom_InsertNothingInRoomQuestion_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomPassword를 입력하지 않는 경우")
    void test_CreateRoom_InsertNothingInRoomPassword_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_CreateRoom_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("코드를 NULL로 입력하는 경우")
    void test_ReadUserRoomJoinByRoomCode_IsNullInCode_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("잘못된 코드를 입력하는 경우")
    void test_ReadUserRoomJoinByRoomCode_InsertWrongCode_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomName을 NULL로 입력하는 경우")
    void test_CreateUserRoomJoin_InsertNullInRoomName_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("userName을 NULL로 입력하는 경우")
    void test_CreateUserRoomJoin_InsertNullInUserName_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("roomPassword를 NULL로 입력하는 경우")
    void test_CreateUserRoomJoin_InsertNullInPassword_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("잘못된 roomPassword를 입력하는 경우")
    void test_CreateUserRoomJoin_InsertWrongPassword_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_CreateUserRoomJoin_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 roomId를 입력하는 경우")
    void test_CreateUserRoomJoin_InValidRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("현재 날짜 이후 날짜를 입력하는 경우")
    void test_CompareFeedByUserIdAndRoomId_InsertExpiredDate_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("잘못된 형식의 Date를 입력하는 경우")
    void test_CompareFeedByUserIdAndRoomId_InsertWrongDateFormat_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않은 roomId를 입력하는 경우")
    void test_CompareFeedByUserIdAndRoomId_InValidRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("feedURL을 입력하지 않는 경우")
    void test_CreateFeed_InsertNothingInFeedURL_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않은 userRoomId를 입력하는 경우")
    void test_CreateFeed_InValidUserRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않은 roomId를 입력하는 경우")
    void test_CreateFeed_InValidRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("현재 날짜 이후 날짜를 입력하는 경우")
    void test_ReadFeedByRoomIdAndDate_InsertExpiredDate_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("방생성 이전 날짜를 입력하는 경우")
    void test_ReadFeedByRoomIdAndDate_InsertBeforeInitDate_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("잘못된 형식의 Date를 입력하는 경우")
    void test_ReadFeedByRoomIdAndDate_InsertWrongDateFormat_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 roomId를 입력하는 경우")
    void test_ReadFeedByRoomIdAndDate_InValidRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("questionId에 NULL을 입력하는 경우")
    void test_ReadQuestionByQuestionId_IsNullInQuestionId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 questionId를 입력하는 경우")
    void test_ReadQuestionByQuestionId_InValidQuestionId_ThrowException() throws Exception {

    }



    @Test
    @DisplayName("존재하지 않은 roomId를 입력하는 경우")
    void test_ReadRoomQuestion_InValidRoomId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_ReadRoomQuestion_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 userId를 입력하는 경우")
    void test_ReadRoomByUserId_InValidUserId_ThrowException() throws Exception {

    }

    @Test
    @DisplayName("userId에 대응되는 room이 존재하지 않는 경우")
    void test_ReadRoomByUserId_NoRoomCorrespondByUserId_ReturnZero() throws Exception {

    }


}