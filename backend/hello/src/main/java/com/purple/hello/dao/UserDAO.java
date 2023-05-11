package com.purple.hello.dao;

import com.purple.hello.dto.in.UpdateUserInfoInDTO;
import com.purple.hello.dto.out.ReadUserInfoOutDTO;
import com.purple.hello.entity.User;

import java.io.IOException;

public interface UserDAO {
    User readUserByOauthId(String oauthId)throws Exception;

    User insertUser(User user);

    void updateRefreshToken(long userId, String refreshToken);

    User isValidRefreshToken(String refreshToken);

    boolean updateUserInfo(UpdateUserInfoInDTO updateUserInfoInDTO) throws Exception;

    ReadUserInfoOutDTO readUserInfoByUserId(long userId);
}
