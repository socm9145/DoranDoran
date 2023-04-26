package com.purple.hello.dao;

import com.purple.hello.entity.User;

public interface UserDAO {
    User readUserByOauthId(String oauthId);

    User insertUser(User user);

    void updateRefreshToken(long userId, String refreshToken);

    User isValidRefreshToken(String refreshToken);
}
