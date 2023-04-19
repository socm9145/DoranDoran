package com.purple.hello.dao.impl;

import com.purple.hello.dao.UserRoomDAO;
import com.purple.hello.dto.in.*;
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

    /**
     * userRoomId와 userId가 일치하면 roomName을 변경시키고 변경된 이름을 그대로 반환하는 함수
     * */
    @Override
    public String updateRoomNameByRoomIdAndUserId(long userId, UpdateRoomNameInDTO updateRoomNameInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.roomName, updateRoomNameInDTO.getRoomName())
                .where(qUserRoom.userRoomId.eq(updateRoomNameInDTO.getUserRoomId()).and(qUserRoom.user.userId.eq(userId)))
                .execute();
        return updateRoomNameInDTO.getRoomName();
    }

    /**
     * userRoomId와 userId가 일치하면 userName을 변경시키고 변경된 이름을 그대로 반환하는 함수
     * */
    @Override
    public String updateUserNameByRoomIdAndUserId(long userId, UpdateUserNameInDTO updateUserNameInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.userName, updateUserNameInDTO.getUserName())
                .where(qUserRoom.userRoomId.eq(updateUserNameInDTO.getUserRoomId()).and(qUserRoom.user.userId.eq(userId)))
                .execute();
        return updateUserNameInDTO.getUserName();
    }

    /**
     * userRoomId와 userId가 일치하면 moveAlarm을 변경시키고 변경된 설정을 그대로 반환하는 함수
     * */
    @Override
    public BoolAlarm updateMoveAlarmByRoomIdAndUserId(long userId, UpdateMoveAlarmInDTO updateMoveAlarmInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.moveAlarm, updateMoveAlarmInDTO.getMoveAlarm())
                .where(qUserRoom.userRoomId.eq(updateMoveAlarmInDTO.getUserRoomId()).and(qUserRoom.user.userId.eq(userId)))
                .execute();

        BoolAlarm boolAlarm = updateMoveAlarmInDTO.getMoveAlarm();
        return boolAlarm;
    }

    @Override
    public BoolAlarm updateSafeAlarmByRoomIdAndUserId(UpdateSafeAlarmInDTO updateSafeAlarmInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qUserRoom);
        jpaUpdateClause.set(qUserRoom.safeAlarm, updateSafeAlarmInDTO.getSafeAlarm())
                .where(qUserRoom.userRoomId.eq(updateSafeAlarmInDTO.getRoomId()).and(qUserRoom.user.userId.eq(
                        updateSafeAlarmInDTO.getUserId())))
                .execute();
        return updateSafeAlarmInDTO.getSafeAlarm();
    }
}
