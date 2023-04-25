package com.purple.hello.service;

import com.purple.hello.dto.in.OauthUserInputDTO;
import com.purple.hello.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<String> getKakaoUserInfoWithAccessToken(String token);
    User readUserByOauthId(long oauthId);

    User insertUser(OauthUserInputDTO oauthUserInputDTO);

    void updateRefreshToken(long userId, String refreshToken);

    User isValidRefreshToken(String refreshToken);
}
