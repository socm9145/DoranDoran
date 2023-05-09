package com.purple.hello.dao.impl;

import com.purple.hello.dao.UserDAO;
import com.purple.hello.dto.in.UpdateUserInfoInDTO;
import com.purple.hello.entity.QUser;
import com.purple.hello.entity.User;
import com.purple.hello.repo.UserRepo;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager em;
    private final UserRepo userRepo;
    private final QUser qUser = QUser.user;
    @Autowired
    public UserDAOImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public User readUserByOauthId(String oauthId) {
        Optional<User> isUser = userRepo.findByOauthId(oauthId);
        return isUser.orElse(null);
    }

    @Override
    public User insertUser(User user) {
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public void updateRefreshToken(long userId, String refreshToken) {
        User user = em.find(User.class, userId);
        user.setRefreshToken(refreshToken);
        em.merge(user);
    }

    @Override
    public User isValidRefreshToken(String refreshToken) {
        return userRepo.findByRefreshToken(refreshToken);
    }

    @Override
    public boolean updateUserInfo(UpdateUserInfoInDTO updateUserInfoInDTO) throws IOException {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUser);
        jpaUpdateClause.set(qUser.birth, updateUserInfoInDTO.getBirth())
                .set(qUser.userProfileUrl, updateUserInfoInDTO.getUserProfileUrl())
                .where(qUser.userId.eq(updateUserInfoInDTO.getUserId()))
                .execute();
        return true;
    }
}
