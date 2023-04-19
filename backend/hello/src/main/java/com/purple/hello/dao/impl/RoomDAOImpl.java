package com.purple.hello.dao.impl;

import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.in.CreateUserRoomInDTO;
import com.purple.hello.dto.out.CreateRoomOutDTO;
import com.purple.hello.dto.out.ReadRoomOutDTO;
import com.purple.hello.dto.out.ReadUserRoomJoinOutDTO;
import com.purple.hello.entity.QRoom;
import com.purple.hello.entity.QUserRoom;
import com.purple.hello.entity.Room;
import com.purple.hello.enu.UserRoomRole;
import com.purple.hello.repo.RoomRepo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Component
public class RoomDAOImpl implements RoomDAO {
    @PersistenceContext
    EntityManager em;
    private final QRoom qRoom = QRoom.room;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;

    @Autowired
    private final RoomRepo roomRepo;
    public RoomDAOImpl(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Override
    public List<ReadRoomOutDTO> readRoomByUserId(long userId) {
        return null;
    }

    @Override
    public CreateRoomOutDTO createRoom(CreateUserRoomInDTO createUserRoomInDTO) {
        Room room = Room.builder()
                .beginTime(0)
                .createAt(new Date())
                .roomCode("room-code")
                .roomQuestion(createUserRoomInDTO.getRoomQuestion())
                .roomPassword(createUserRoomInDTO.getRoomPassword())
                .build();

        room = roomRepo.save(room);

        CreateRoomOutDTO createRoomOutDTO = CreateRoomOutDTO.builder()
                .roomId(room.getRoomId())
                .build();

        return createRoomOutDTO;
    }

    @Override
    public boolean comparePasswordByRoomCode(long roomId, String password) {
        Room room = this.roomRepo.findById(roomId).get();

        return room.getRoomPassword().equals(password);
    }

    @Override
    public ReadUserRoomJoinOutDTO readUserRoomJoinByRoomCode(String roomCode) {
        // 조인이 맞나 ? in이 맞나 ?
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
}
