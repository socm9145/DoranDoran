package com.purple.hello.controller;

import com.purple.hello.dto.in.OauthUserInputDTO;
import com.purple.hello.entity.User;
import com.purple.hello.jwt.JwtTokenProvider;
import com.purple.hello.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;

import com.purple.hello.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
    @Value("${jwt.secret}")
    private String secret;
    AccountController(AlarmService alarmService, FeedService feedService, QuestionService questionService, RoomService roomService,
                   UserRoomService userRoomService, UserService userService){
        this.alarmService = alarmService;
        this.feedService = feedService;
        this.questionService = questionService;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
    }

    @GetMapping("/login/kakao")
    public ResponseEntity<String> loginKakao(@RequestHeader("id-token") String token, HttpServletResponse httpServletResponse){
        ResponseEntity<String> response = userService.getKakaoUserInfoWithAccessToken(token);
        JSONObject jsonObject = new JSONObject(response.getBody());
        long oauthId = (long) jsonObject.get("id");

        User user = userService.readUserByOauthId(oauthId);

        if(user == null){
            OauthUserInputDTO oauthUserInputDTO = new OauthUserInputDTO(oauthId);
            user = userService.insertUser(oauthUserInputDTO);
        }

        final String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
        final String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getUserId()));

        userService.updateRefreshToken(user.getUserId(), refreshToken);

        httpServletResponse.addHeader("Access-Token", accessToken);
        httpServletResponse.addHeader("Refresh-Token", refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body("LOGIN SUCCESS");
    }

    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestHeader("Access-Token") String accessToken,
                                     @RequestHeader("Refresh-Token") String refreshToken,
                                     HttpServletResponse response){
        if(accessToken == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효한 AccessToken이 아닙니다");
        }

        User user = userService.isValidRefreshToken(refreshToken);

        if(user != null){
            try{
                Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(refreshToken).getBody();
                Date expiration = claims.getExpiration();
                if(expiration.before(new Date(System.currentTimeMillis()))){
                    throw new JwtException("Expired JWT token");
                }
                accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getUserId()));
                refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getUserId()));

                userService.updateRefreshToken(user.getUserId(), refreshToken);

                response.addHeader("Access-Token", accessToken);
                response.addHeader("Refresh-Token", refreshToken);
                return ResponseEntity.status(HttpStatus.OK).body("REISSUE SUCCESS");
            } catch (JwtException e){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token Expired");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 Refresh Token");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        long userId = (long) httpServletRequest.getAttribute("userId");

        httpServletResponse.addHeader("Access-Token", null);
        httpServletResponse.addHeader("Refresh-Token", null);

        return ResponseEntity.status(HttpStatus.OK).body("LOGOUT SUCCESS");
    }
    
}
