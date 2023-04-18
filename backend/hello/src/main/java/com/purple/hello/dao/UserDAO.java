package com.purple.hello.dao;

import com.purple.hello.entity.User;

public interface UserDAO {
    User readUserByOauthId(long oauthId);

    User insertUser(User user);

    void updateRefreshToken(long userId, String refreshToken);
}
