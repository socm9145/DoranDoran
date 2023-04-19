package com.purple.hello.controller;

import com.purple.hello.dto.in.OauthUserInputDTO;
import com.purple.hello.entity.User;
import com.purple.hello.jwt.JwtTokenProvider;
import com.purple.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;

import com.purple.hello.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/account", produces = "application/json; charset=utf-8")
public class AccountController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final AlarmService alarmService;
    @Autowired
    private final FeedService feedService;
    @Autowired
    private final QuestionService questionService;
    @Autowired
    private final RoomService roomService;
    @Autowired
    private final UserRoomService userRoomService;
    @Autowired
    private final UserService userService;
    AccountController(AlarmService alarmService, FeedService feedService, QuestionService questionService, RoomService roomService,
                   UserRoomService userRoomService, UserService userService){
        this.alarmService = alarmService;
        this.feedService = feedService;
        this.questionService = questionService;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("id-token") String token, HttpServletResponse httpServletResponse){
        ResponseEntity<String> response = userService.getKakaoUserInfoWithAccessToken(token);

        // 회원 정보 추출
        JSONObject jsonObject = new JSONObject(response.getBody());
        long oauthId = (long) jsonObject.get("id");

        User user = userService.readUserByOauthId(oauthId);

        // 신규 회원인 경우
        if(user == null){
            JSONObject userInfo = jsonObject.getJSONObject("kakao_account");
            userInfo = userInfo.getJSONObject("profile");
            String name = userInfo.getString("nickname");

            OauthUserInputDTO oauthUserInputDTO = new OauthUserInputDTO(oauthId, name);
            user = userService.insertUser(oauthUserInputDTO);
        }

        final String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
        final String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getUserId()), accessToken);

        userService.updateRefreshToken(user.getUserId(), refreshToken);

        httpServletResponse.addHeader("Access-Token", accessToken);
        httpServletResponse.addHeader("Refresh-Token", refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body("LOGIN SUCCESS");
    }

    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestHeader("Access-Token") String accessToken,
                                     @RequestHeader("Refresh-Token") String refreshToken,
                                     HttpServletResponse httpServletResponse){
        if(accessToken == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효한 AccessToken이 아닙니다");
        }
        // TODO App 과 협업으로 추가 작성 예정 (refresh token 유효성 확인, 유효하면?, 유효하지 않으면)

        long userId = userService.isValidRefreshToken(refreshToken);
        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        long userId = (long) httpServletRequest.getAttribute("userId");

        return "User ID: " + userId;
    }
    
}
