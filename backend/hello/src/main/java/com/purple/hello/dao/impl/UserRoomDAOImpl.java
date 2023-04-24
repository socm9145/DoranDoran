package com.purple.hello.dao.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.CreateUserRoomJoinOutDTO;
import com.purple.hello.entity.*;
import com.purple.hello.enu.BoolAlarm;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.repo.UserRepo;
import com.purple.hello.repo.UserRoomRepo;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Optional;

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
    public CreateRoomOutDTO createUserRoom(CreateUserRoomInDTO createUserRoomInDTO, long roomId) {
        Optional<Room> room = this.roomRepo.findById(roomId);
        Optional<User> user = this.userRepo.findById(createUserRoomInDTO.getUserId());

        if (room.isEmpty() || user.isEmpty())
            return null;

        UserRoom userRoom = UserRoom.builder()
                .userRoomRole(UserRoomRole.ROLE1)
                .userName(createUserRoomInDTO.getUserName())
                .roomName(createUserRoomInDTO.getRoomName())
                .safeAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .dayAlarm(BoolAlarm.Y)
                .createAt(new Date())
                .room(room.get())
                .user(user.get())
                .build();

        room.get().getUserRoom().add(userRoom);
        user.get().getUserRoom().add(userRoom);

        UserRoom rUserRoom = this.userRoomRepo.save(userRoom);

        CreateRoomOutDTO createRoomOutDTO = CreateRoomOutDTO.builder()
                .userRoomId(rUserRoom.getUserRoomId())
                .userRoomRole(rUserRoom.getUserRoomRole())
                .userName(rUserRoom.getUserName())
                .roomName(rUserRoom.getRoomName())
                .safeAlarm(rUserRoom.getSafeAlarm())
                .moveAlarm(rUserRoom.getSafeAlarm())
                .dayAlarm(rUserRoom.getDayAlarm())
                .createAt(rUserRoom.getCreateAt())
                .build();

        return createRoomOutDTO;
    }

    @Override
    public CreateUserRoomJoinOutDTO createUserRoomJoin(CreateUserRoomJoinInDTO createUserRoomJoinInDTO) {
        Optional<Room> room = this.roomRepo.findById(createUserRoomJoinInDTO.getRoomId());
        Optional<User> user = this.userRepo.findById(createUserRoomJoinInDTO.getUserId());

        if (room.isEmpty() || user.isEmpty())
            return null;

        UserRoom userRoom = UserRoom.builder()
                .createAt(new Date())
                .dayAlarm(BoolAlarm.Y)
                .moveAlarm(BoolAlarm.Y)
                .safeAlarm(BoolAlarm.Y)
                .roomName(createUserRoomJoinInDTO.getRoomName())
                .userName(createUserRoomJoinInDTO.getUserName())
                .userRoomRole(UserRoomRole.ROLE2)
                .user(user.get())
                .room(room.get())
                .build();

        room.get().getUserRoom().add(userRoom);
        user.get().getUserRoom().add(userRoom);

        UserRoom rUserRoom = this.userRoomRepo.save(userRoom);

        CreateUserRoomJoinOutDTO createUserRoomJoinOutDTO = CreateUserRoomJoinOutDTO
                .builder()
                .createAt(rUserRoom.getCreateAt())
                .userRoomRole(rUserRoom.getUserRoomRole())
                .userRoomId(rUserRoom.getUserRoomId())
                .userName(rUserRoom.getUserName())
                .roomName(rUserRoom.getRoomName())
                .dayAlarm(rUserRoom.getDayAlarm())
                .moveAlarm(rUserRoom.getMoveAlarm())
                .safeAlarm(rUserRoom.getSafeAlarm())
                .build();

        return createUserRoomJoinOutDTO;
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
}
