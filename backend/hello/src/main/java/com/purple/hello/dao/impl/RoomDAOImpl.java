package com.purple.hello.dao.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomPasswordInDTO;
import com.purple.hello.dto.in.UpdateRoomCodeInDTO;
import com.purple.hello.dto.in.DeleteRoomInDTO;
import com.purple.hello.dto.out.*;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.tool.MemberDTO;
import com.purple.hello.dto.tool.ReadRoomDTO;
import com.purple.hello.entity.*;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.HistoryRepo;
import com.purple.hello.repo.RoomRepo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import java.util.*;

@Component
public class RoomDAOImpl implements RoomDAO {
    @PersistenceContext
    EntityManager em;
    private final QRoom qRoom = QRoom.room;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;
    private final QUser qUser = QUser.user;
    @Autowired
    private final RoomRepo roomRepo;
    @Autowired
    private final HistoryRepo historyRepo;
    public RoomDAOImpl(RoomRepo roomRepo, HistoryRepo historyRepo) {
        this.roomRepo = roomRepo;
        this.historyRepo = historyRepo;
    }

    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) {

        String sql = "select UR.user_room_id, R.room_id, UR.room_name, UR2.user_name, U2.user_profile_url, U2.user_id" +
                "        from users U " +
                "        join user_rooms UR " +
                "        on U.user_id = UR.user_id " +
                "        join rooms R " +
                "        on UR.room_id = R.room_id " +
                "        join user_rooms UR2 " +
                "        on UR2.room_id = R.room_id " +
                "        join users U2 " +
                "        on U2.user_id = UR2.user_id " +
                "        where U.user_id = :userId " +
                "        and UR.user_room_role != :userRoomRole " +
                "        and UR2.user_room_role != :userRoomRole";
        Query query = em.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.setParameter("userRoomRole", UserRoomRole.ROLE3.name());

        List resultList = query.getResultList();

        if (resultList.size() == 0)
            return null;

        Map<Long, List<ReadRoomDTO>> map = new HashMap<>();

        for (Object o : resultList){

            Object [] result = (Object[]) o;
            long userRoomId = ((Number)result[0]).longValue();
            long roomId = ((Number)result[1]).longValue();
            long rUserId = ((Number)result[5]).longValue();
            if (!map.containsKey(roomId))
                map.put(roomId, new ArrayList<>());
            
            map.get(roomId).add(ReadRoomDTO.builder()
                            .userRoomId(userRoomId)
                            .roomId(roomId)
                            .roomName((String)result[2])
                            .userName((String)result[3])
                            .userProfileUrl((String)result[4])
                            .userId(rUserId)
                            .build());
        }

        List<ReadRoomOutDTO> readRoomOutDTOs = new ArrayList<>();

        for (long key : map.keySet()){
            ReadRoomOutDTO readRoomOutDTO = new ReadRoomOutDTO();
            readRoomOutDTO.setUserRoomId(map.get(key).get(0).getUserRoomId());
            readRoomOutDTO.setRoomId(key);
            readRoomOutDTO.setRoomName(map.get(key).get(0).getRoomName());

            List<MemberDTO> memberDTOs = new ArrayList<>();

            for (ReadRoomDTO readRoomDTO : map.get(key)){
                MemberDTO memberDTO = MemberDTO.builder()
                        .userId(readRoomDTO.getUserId())
                        .name(readRoomDTO.getUserName())
                        .profileUrl(readRoomDTO.getUserProfileUrl())
                        .build();

                memberDTOs.add(memberDTO);
            }
            readRoomOutDTO.setMembers(memberDTOs);
            readRoomOutDTOs.add(readRoomOutDTO);
        }

        return readRoomOutDTOs;
    }

    @Override
    public CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) throws Exception{
        if (createUserRoomInDTO.getUserName() == null)
            throw new IllegalArgumentException();

        if (createUserRoomInDTO.getRoomName() == null)
            throw new IllegalArgumentException();

        if (createUserRoomInDTO.getRoomPassword() == null)
            throw new IllegalArgumentException();

        if (createUserRoomInDTO.getRoomQuestion() == null)
            throw new IllegalArgumentException();

        Room room = Room.builder()
                .beginTime(8)
                .createAt(new Date())
                .roomQuestion(createUserRoomInDTO.getRoomQuestion())
                .roomPassword(createUserRoomInDTO.getRoomPassword())
                .build();

        room = roomRepo.save(room);

        return CreateRoomDTO.builder()
                .roomId(room.getRoomId())
                .build();
    }

    @Override
    public String comparePasswordByRoomCode(long roomId) {

        Optional<Room> room = this.roomRepo.findById(roomId);
        if (room.isEmpty())
            return null;

        return room.get().getRoomPassword();
    }

    @Override
    public ReadUserRoomJoinOutDTO readUserRoomJoinByRoomCode(String roomCode) {

        List<ReadUserRoomJoinOutDTO> readUserRoomJoinOutDTOs = new JPAQuery<>(em)
                .select(Projections.constructor(ReadUserRoomJoinOutDTO.class, qRoom.roomId, qUserRoom.roomName, qRoom.roomQuestion))
                .from(qRoom)
                .join(qUserRoom)
                .on(qRoom.roomId.eq(qUserRoom.room.roomId))
                .where(qRoom.roomCode.eq(roomCode))
                .where(qUserRoom.userRoomRole.eq(UserRoomRole.ROLE1))
                .fetch();

        if (readUserRoomJoinOutDTOs.size() == 0)
            return null;

        ReadUserRoomJoinOutDTO readUserRoomJoinOutDTO = ReadUserRoomJoinOutDTO.builder()
                .roomId(readUserRoomJoinOutDTOs.get(0).getRoomId())
                .roomQuestion(readUserRoomJoinOutDTOs.get(0).getRoomQuestion())
                .roomName(readUserRoomJoinOutDTOs.get(0).getRoomName())
                .build();

        return  readUserRoomJoinOutDTO;
    }

    @Override
    public boolean updateRoomPassword(UpdateRoomPasswordInDTO updateRoomPasswordInDTO) {
        UserRoomRole userRoomRole = new JPAQuery<>(em)
                .select(qUserRoom.userRoomRole)
                .from(qUserRoom)
                .where(qUserRoom.room.roomId.eq(updateRoomPasswordInDTO.getRoomId())
                        .and(qUserRoom.user.userId.eq(updateRoomPasswordInDTO.getUserId())))
                .fetchOne();

        if(userRoomRole == null)
            throw new IllegalArgumentException();

        if(updateRoomPasswordInDTO.getRoomQuestion() == null) {
            //updateRoomPasswordInDTO.setRoomQuestion("비밀번호를 입력하세요.");
            throw new IllegalArgumentException();
        }

        if(userRoomRole == UserRoomRole.ROLE1){
            JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qRoom);
            jpaUpdateClause.set(qRoom.roomQuestion, updateRoomPasswordInDTO.getRoomQuestion())
                    .set(qRoom.roomPassword, updateRoomPasswordInDTO.getRoomPassword())
                    .where(qRoom.roomId.eq(updateRoomPasswordInDTO.getRoomId()))
                    .execute();
        }

        return true;
    }
    public String readRoomCodeByRoomId(long roomId) {
        Optional<Room> room = roomRepo.findById(roomId);

        if (room.isEmpty())
            return null;

        String priUrl = room.get().getRoomCode();
        return priUrl;
    }

    @Override
    @Transactional
    public void updateRoomCodeByRoomId(UpdateRoomCodeInDTO updateRoomCodeInDTO) {
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qRoom);
        jpaUpdateClause.set(qRoom.roomCode, updateRoomCodeInDTO.getRoomCode())
                .where(qRoom.roomId.eq(updateRoomCodeInDTO.getRoomId()))
                .execute();
    }

    public boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) throws Exception{
        Room room = this.roomRepo.findById(deleteRoomInDTO.getRoomId()).get();

        Long count = new JPAQueryFactory(em)
                .selectFrom(qUser)
                .join(qUserRoom)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qRoom)
                .on(qRoom.roomId.eq(qUserRoom.room.roomId))
                .where(qRoom.roomId.eq(deleteRoomInDTO.getRoomId()))
                .where(qUser.userId.eq(deleteRoomInDTO.getUserId()))
                .where(qUserRoom.userRoomRole.eq(UserRoomRole.ROLE1))
                .fetchCount();

        if (count == 0)
            throw new IllegalArgumentException();

        this.roomRepo.delete(room);
        return true;
    }

    public LocalDate getCreatedAtByRoomId(long roomId){
        Room room = roomRepo.findRoomByRoomId(roomId);
        LocalDate localDate = room.getCreateAt().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate;
    }

    @Override
    public ReadRoomQuestionOutDTO readRoomQuestionByRoomIdAndUserId(long roomId, long userId) {
        ReadRoomQuestionOutDTO readRoomQuestionOutDTO = new JPAQuery<>(em)
                .select(Projections.constructor(ReadRoomQuestionOutDTO.class, qRoom.roomId, qRoom.roomQuestion))
                .from(qRoom)
                .join(qUserRoom)
                .on(qRoom.roomId.eq(qUserRoom.room.roomId))
                .join(qUser)
                .on(qUserRoom.user.userId.eq(qUser.userId))
                .where(qRoom.roomId.eq(roomId).and(qUser.userId.eq(userId)))
                .fetchOne();
        return readRoomQuestionOutDTO;
    }

    @Override
    public List<MemberDTO> readMemberListByRoomId(long roomId, long userId) {
        Long rRoomId = new JPAQuery<>(em)
                .select(Projections.constructor(Long.class, qRoom.roomId))
                .from(qRoom)
                .join(qUserRoom)
                .on(qRoom.roomId.eq(qUserRoom.room.roomId))
                .join(qUser)
                .on(qUserRoom.user.userId.eq(qUser.userId))
                .where(qRoom.roomId.eq(roomId).and(qUser.userId.eq(userId)).and(qUserRoom.userRoomRole.ne(UserRoomRole.ROLE3)))
                .fetchOne();
        if(rRoomId == null){
            return null;
        }
        List<MemberDTO> memberDTOs = new JPAQuery<>(em)
                .select(Projections.constructor(MemberDTO.class, qUser.userId, qUserRoom.roomName, qUser.userProfileUrl))
                .from(qUser)
                .join(qUserRoom)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qRoom)
                .on(qUserRoom.room.roomId.eq(qRoom.roomId))
                .where(qRoom.roomId.eq(roomId).and(qUserRoom.userRoomRole.ne(UserRoomRole.ROLE3)))
                .fetch();
        return memberDTOs;
    }

    @Override
    public Integer readBeginTimeByRoomId(long roomId) {
        return new JPAQuery<>(em)
                .select(qRoom.beginTime)
                .from(qRoom)
                .where(qRoom.roomId.eq(roomId))
                .fetchOne();
    }
}
