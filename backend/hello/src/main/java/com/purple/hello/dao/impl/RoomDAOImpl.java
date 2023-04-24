package com.purple.hello.dao.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.in.UpdateRoomPasswordInDTO;
import com.purple.hello.dto.in.UpdateRoomCodeInDTO;
import com.purple.hello.dto.in.DeleteRoomInDTO;
import com.purple.hello.dto.tool.CreateRoomDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.out.ReadUserRoomJoinOutDTO;
import com.purple.hello.dto.tool.MemberDTO;
import com.purple.hello.dto.tool.ReadRoomDTO;
import com.purple.hello.entity.*;
import com.purple.hello.enu.UserRoomRole;
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
    public RoomDAOImpl(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) {

        String sql = "select UR.user_room_id, R.room_id, UR.room_name, UR2.user_name, U2.user_profile_url " +
                "        from users U " +
                "        join user_rooms UR " +
                "        on U.user_id = UR.user_id " +
                "        join rooms R " +
                "        on UR.room_id = R.room_id " +
                "        join user_rooms UR2 " +
                "        on UR2.room_id = R.room_id " +
                "        join users U2 " +
                "        on U2.user_id = UR2.user_id " +
                "        where U.user_id = :userId";
        Query query = em.createNativeQuery(sql);
        query.setParameter("userId", userId);

        List resultList = query.getResultList();

        if (resultList.size() == 0)
            return null;

        Map<Long, List<ReadRoomDTO>> map = new HashMap<>();

        for (Object o : resultList){

            Object [] result = (Object[]) o;
            long userRoomId = ((Number)result[0]).longValue();
            long roomId = ((Number)result[1]).longValue();

            if (!map.containsKey(roomId))
                map.put(roomId, new ArrayList<>());
            
            map.get(roomId).add(ReadRoomDTO.builder()
                            .userRoomId(userRoomId)
                            .roomId(roomId)
                            .roomName((String)result[2])
                            .userName((String)result[3])
                            .userProfileUrl((String)result[4])
                            .build());
        }

        List<ReadRoomOutDTO> readRoomOutDTOs = new ArrayList<>();

        for (long key : map.keySet()){
            ReadRoomOutDTO readRoomOutDTO = new ReadRoomOutDTO();
            readRoomOutDTO.setUserRoomId(map.get(key).get(0).getUserRoomId());
            readRoomOutDTO.setRoomId(key);
            readRoomOutDTO.setRoomName(map.get(key).get(0).getRoomName());

            List<MemberDTO> memberDTOs = new ArrayList<>();

            for (ReadRoomDTO readRoomDTOs : map.get(key)){
                MemberDTO memberDTO = MemberDTO.builder()
                        .name(readRoomDTOs.getUserName())
                        .profileUrl(readRoomDTOs.getUserProfileUrl())
                        .build();

                memberDTOs.add(memberDTO);
            }
            readRoomOutDTO.setMembers(memberDTOs);
            readRoomOutDTOs.add(readRoomOutDTO);
        }

        return readRoomOutDTOs;
    }

    @Override
    public CreateRoomDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++)
            sb.append((char)(97 + (int)(Math.random()*26)));

        Room room = Room.builder()
                .beginTime(0)
                .createAt(new Date())
                .roomCode(sb.toString())
                .roomQuestion(createUserRoomInDTO.getRoomQuestion())
                .roomPassword(createUserRoomInDTO.getRoomPassword())
                .build();

        room = roomRepo.save(room);

        CreateRoomDTO createRoomDTO = CreateRoomDTO.builder()
                .roomId(room.getRoomId())
                .build();

        return createRoomDTO;
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

        if (userRoomRole == null)
            return false;

        if(userRoomRole == UserRoomRole.ROLE1){
            JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, qRoom);
            jpaUpdateClause.set(qRoom.roomPassword, updateRoomPasswordInDTO.getRoomPassword())
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

    public boolean deleteRoom(DeleteRoomInDTO deleteRoomInDTO) {
        Room room = this.roomRepo.findById(deleteRoomInDTO.getRoomId()).get();

        //List<DeleteRoomDTO> deleteRoomDTOs = new JPAQuery<>(em)
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
            return false;

        this.roomRepo.delete(room);

        return true;
    }
}
