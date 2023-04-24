package com.purple.hello.dao.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
import com.purple.hello.entity.*;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.repo.UserRepo;
import com.purple.hello.repo.UserRoomRepo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Component
public class UserRoomDAOImpl implements UserRoomDAO {

    @PersistenceContext
    private EntityManager em;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;
    @Autowired
    private final UserRoomRepo userRoomRepo;
    @Autowired
    private final RoomRepo roomRepo;
    @Autowired
    private final UserRepo userRepo;
    public UserRoomDAOImpl(UserRoomRepo userRoomRepo, RoomRepo roomRepo, UserRepo userRepo){
        this.userRoomRepo = userRoomRepo;
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
    }
    @Override
    public void createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) {
        Room room = this.roomRepo.getById(roomId);
        User user = this.userRepo.getById(createUserRoomInDTO.getUserId());

        UserRoom userRoom = UserRoom.builder()
                .userRoomRole(UserRoomRole.ROLE1)
                .userName(createUserRoomInDTO.getUserName())
                .roomName(createUserRoomInDTO.getRoomName())
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .dayAlarm(BoolAlarm.Y)
                .createAt(new Date())
                .room(room)
                .user(user)
                .build();

        room.getUserRoom().add(userRoom);
        user.getUserRoom().add(userRoom);

        this.userRoomRepo.save(userRoom);
    }

    @Override
    public void createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) {
        Room room = this.roomRepo.getById(createUserRoomJoinInDTO.getRoomId());
        User user = this.userRepo.getById(createUserRoomJoinInDTO.getUserId());

        UserRoom userRoom = UserRoom.builder()
                .createAt(new Date())
                .dayAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .safeAlarm(BoolAlarm.Y)
                .roomName(createUserRoomJoinInDTO.getRoomName())
                .userName(createUserRoomJoinInDTO.getUserName())
                .userRoomRole(UserRoomRole.ROLE2)
                .user(user)
                .room(room)
                .build();

        room.getUserRoom().add(userRoom);
        user.getUserRoom().add(userRoom);

        this.userRoomRepo.save(userRoom);
    }

    @Override
    public String updateRoomName(long userId, UpdateRoomNameInDTO updateRoomNameInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.roomName, updateRoomNameInDTO.getRoomName())
                .where(qUserRoom.userRoomId.eq(updateRoomNameInDTO.getUserRoomId())
                        .and(qUserRoom.user.userId.eq(userId)))
                .execute();
        return updateRoomNameInDTO.getRoomName();
    }

    @Override
    public String updateUserName(long userId, UpdateUserNameInDTO updateUserNameInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.userName, updateUserNameInDTO.getUserName())
                .where(qUserRoom.userRoomId.eq(updateUserNameInDTO.getUserRoomId())
                        .and(qUserRoom.user.userId.eq(userId)))
                .execute();
        return updateUserNameInDTO.getUserName();
    }

    @Override
    public BoolAlarm updateMoveAlarm(long userId, UpdateMoveAlarmInDTO updateMoveAlarmInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.moveAlarm, updateMoveAlarmInDTO.getMoveAlarm())
                .where(qUserRoom.userRoomId.eq(updateMoveAlarmInDTO.getUserRoomId())
                        .and(qUserRoom.user.userId.eq(userId)))
                .execute();

        BoolAlarm boolAlarm = updateMoveAlarmInDTO.getMoveAlarm();
        return boolAlarm;
    }

    @Override
    public BoolAlarm updateSafeAlarm(UpdateSafeAlarmInDTO updateSafeAlarmInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.safeAlarm, updateSafeAlarmInDTO.getSafeAlarm())
                .where(qUserRoom.userRoomId.eq(updateSafeAlarmInDTO.getRoomId())
                        .and(qUserRoom.user.userId.eq(updateSafeAlarmInDTO.getUserId())))
                .execute();
        return updateSafeAlarmInDTO.getSafeAlarm();
    }

    @Override
    public void deleteUserRoom(DeleteUserRoomInDTO deleteUserRoomInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.userRoomRole, UserRoomRole.ROLE3)
                .where(qUserRoom.userRoomId.eq(deleteUserRoomInDTO.getUserRoomId())
                        .and(qUserRoom.user.userId.eq(deleteUserRoomInDTO.getUserId())))
                .execute();
    }

    @Override
    public UserRoom readUserRoomByUserIdAndRoomId(long userId, long roomId) {
        UserRoom userRoom = new JPAQuery<>(em)
                .select(qUserRoom)
                .from(qUserRoom)
                .where(qUserRoom.room.roomId.eq(roomId).and(qUserRoom.user.userId.eq(userId)))
                .fetchOne();
        return userRoom;
    }

    @Override
    public void updateUserRoomRejoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) {
        UserRoom userRoom = readUserRoomByUserIdAndRoomId(createUserRoomJoinInDTO.getUserId(), createUserRoomJoinInDTO.getRoomId());

        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.userRoomRole, UserRoomRole.ROLE2)
                .set(qUserRoom.userName, createUserRoomJoinInDTO.getUserName())
                .set(qUserRoom.roomName, createUserRoomJoinInDTO.getRoomName())
                .where(qUserRoom.userRoomId.eq(userRoom.getUserRoomId()))
                .execute();
    }

    @Override
    public void updateUserRoomRoleByUserRoomId(long userRoomId, UserRoomRole userRoomRole) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.userRoomRole, userRoomRole)
                .where(qUserRoom.userRoomId.eq(userRoomId))
                .execute();
    }

    @Override
    public UserRoom readUserRoomByUserRoomId(long userRoomId) {
        UserRoom userRoom = new JPAQuery<>(em)
                .select(qUserRoom.userRoom)
                .from(qUserRoom)
                .where(qUserRoom.userRoomId.eq(userRoomId))
                .fetchOne();
        return userRoom;
    }

    @Override
    public List<UserRoom> readUserRoomsByRoomIdWithoutUserRoomIdUsingLimit(long roomId, long userRoomId, int limit) {
        List<UserRoom> userRooms = new JPAQuery<>(em)
                .select(qUserRoom)
                .from(qUserRoom)
                .where(qUserRoom.room.roomId.eq(roomId)
                        .and(qUserRoom.userRoomId.ne(userRoomId))
                        .and(qUserRoom.userRoomRole.ne(UserRoomRole.ROLE3)))
                .limit(limit)
                .fetch();
        return userRooms;
    }
}
