package com.purple.hello.dao.impl;

import com.purple.hello.dao.FeedDAO;
import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;
import com.purple.hello.dto.out.ReadFeedOutDTO;
import com.purple.hello.dto.tool.UserIdDTO;
import com.purple.hello.dto.tool.UserIdDateDTO;
import com.purple.hello.entity.*;
import com.purple.hello.repo.FeedRepo;
import com.purple.hello.repo.UserRoomRepo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FeedDAOImpl implements FeedDAO {
    @PersistenceContext
    EntityManager em;
    private final QFeed qFeed = QFeed.feed;
    private final QUser qUser = QUser.user;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;
    private final QRoom qRoom = QRoom.room;
    @Autowired
    private final FeedRepo feedRepo;
    private final UserRoomRepo userRoomRepo;
    public FeedDAOImpl(FeedRepo feedRepo, UserRoomRepo userRoomRepo){
        this.feedRepo = feedRepo;
        this.userRoomRepo = userRoomRepo;
    }

    @Override
    public List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdAndDate(long roomId, Date date) {
        // 특정 그룹방에서 특정 날짜에 피드를 올린 유저의 ID를 반환
        List<UserIdDateDTO> userIdDateDTOs = new JPAQuery<>(em)
                .select(Projections.constructor(UserIdDateDTO.class, qUser.userId, qFeed.createAt))
                .from(qRoom)
                .join(qUserRoom)
                .on(qRoom.roomId.eq(qUserRoom.room.roomId))
                .join(qUser)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qFeed)
                .on(qFeed.userRoom.userRoomId.eq(qUserRoom.userRoomId))
                .where(qRoom.roomId.eq(roomId))
                .fetch();

        if (userIdDateDTOs.isEmpty())
            return null;

        List<UserIdDTO> userIdDTOs = new ArrayList<>();

        for (UserIdDateDTO userIdDateDTO : userIdDateDTOs){
            if (userIdDateDTO.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    .equals(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
                userIdDTOs.add(new UserIdDTO(userIdDateDTO.getUserId()));
            }
        }

        // 특정 그룹방의 모든 회원을 반환
        List<UserIdDTO> allUserIdDTOs = new JPAQuery<>(em)
                .select(Projections.constructor(UserIdDTO.class, qUser.userId))
                .distinct()
                .from(qUser)
                .join(qUserRoom)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qRoom)
                .on(qRoom.roomId.eq(qUserRoom.room.roomId))
                .fetch();

        // 특정 그룹방 모든 인원과 피드를 올린 인원을 비교하여 DTO에 값 부여
        List<CompareFeedByRoomIdOutDTO> compareFeedByRoomIdOutDTOs = new ArrayList<>();

        lp : for (UserIdDTO allUserIdDTO : allUserIdDTOs){
            for (UserIdDTO userIdDTO:userIdDTOs){
                if (userIdDTO.getUserId() == allUserIdDTO.getUserId()){
                    compareFeedByRoomIdOutDTOs.add(new CompareFeedByRoomIdOutDTO(userIdDTO.getUserId(), true));
                        continue lp;
                }
            }
            compareFeedByRoomIdOutDTOs.add(new CompareFeedByRoomIdOutDTO(allUserIdDTO.getUserId(), false));
        }

        return compareFeedByRoomIdOutDTOs;
    }

    @Override
    public CreateFeedOutDTO createFeed(CreateFeedInDTO createFeedInDTO) {
        if (this.userRoomRepo.findById(createFeedInDTO.getUserRoomId()).isEmpty())
            return null;

        // feed 데이터 저장
        Feed feed = Feed.builder()
                .feedUrl(createFeedInDTO.getFeedUrl())
                .content(createFeedInDTO.getContent())
                .feedType(createFeedInDTO.getFeedType())
                .createAt(new Date())
                .build();

        // 연관관계 설정
        UserRoom userRoom = this.userRoomRepo.getById(createFeedInDTO.getUserRoomId());

        userRoom.getFeed().add(feed);
        feed.setUserRoom(userRoom);

        // DB(영속성 콘텍스트) 저장
        Feed rFeed = this.feedRepo.save(feed);

        // 반환 객체 초기화
        CreateFeedOutDTO createFeedOutDTO = CreateFeedOutDTO
                .builder()
                .feedId(rFeed.getFeedId())
                .content(rFeed.getContent())
                .feedType(rFeed.getFeedType())
                .feedUrl(rFeed.getFeedUrl())
                .createAt(new Date())
                .userRoomId(userRoom.getUserRoomId())
                .build();

        // 반환
        return createFeedOutDTO;
    }

    @Override
    public List<ReadFeedOutDTO> readFeedByRoomIdAndDate(long roomId, Date date) {
        List<ReadFeedOutDTO> readFeedOutDTOs = new JPAQuery<>(em)
                .select(Projections.constructor(ReadFeedOutDTO.class, qFeed.feedId, qFeed.feedUrl, qFeed.content, qUser.userId, qFeed.createAt))
                .from(qUser)
                .join(qUserRoom)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qRoom)
                .on(qRoom.roomId.eq(qUserRoom.room.roomId))
                .join(qFeed)
                .on(qFeed.userRoom.userRoomId.eq(qUserRoom.userRoomId))
                .where(qRoom.roomId.eq(roomId))
                .fetch();

        List<ReadFeedOutDTO> rReadFeedOutDTOs = new ArrayList<>();

        for (ReadFeedOutDTO readFeedOutDTO : readFeedOutDTOs){
            if (readFeedOutDTO.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    .equals(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
                rReadFeedOutDTOs.add(readFeedOutDTO);
        }

        return rReadFeedOutDTOs;
    }
}
