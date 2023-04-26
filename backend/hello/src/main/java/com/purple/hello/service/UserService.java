package com.purple.hello.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.purple.hello.dto.in.OauthUserInputDTO;
import com.purple.hello.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public interface UserService {
    ResponseEntity<String> getKakaoUserInfoWithAccessToken(String token);
    Payload googleIdTokenVerify(String idToken) throws GeneralSecurityException, IOException;
    User readUserByOauthId(String oauthId);

    User insertUser(OauthUserInputDTO oauthUserInputDTO);

    void updateRefreshToken(long userId, String refreshToken);

    User isValidRefreshToken(String refreshToken);
}
