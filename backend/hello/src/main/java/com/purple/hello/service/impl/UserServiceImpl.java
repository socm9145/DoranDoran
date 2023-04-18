package com.purple.hello.service.impl;

import com.purple.hello.dao.UserDAO;
import com.purple.hello.dto.in.OauthUserInputDTO;
import com.purple.hello.entity.User;
import com.purple.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    /**
     * 응답 받은 카카오 서버용 액세스토큰 요청한 뒤 유저 정보를 응답 받음
     * */
    @Override
    public ResponseEntity<String> getKakaoUserInfoWithAccessToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        return restTemplate.postForEntity(url, request, String.class);
    }

    @Override
    public User readUserByOauthId(long oauthId) {
        return userDAO.readUserByOauthId(oauthId);
    }

    @Override
    public User insertUser(OauthUserInputDTO oauthUserInputDTO) {
        User user = new User();
        user.setName(oauthUserInputDTO.getName());
        user.setOauthId(oauthUserInputDTO.getOauthId());
        return userDAO.insertUser(user);
    }

    @Override
    public void updateRefreshToken(long userId, String refreshToken) {
        userDAO.updateRefreshToken(userId, refreshToken);
    }

    @Override
    public long isValidRefreshToken(String refreshToken) {
        return 0;
    }
}
