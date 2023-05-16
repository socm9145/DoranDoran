package com.purple.hello.dao.impl;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.dto.tool.DeviceWithQuestionDTO;
import com.purple.hello.entity.*;
import com.purple.hello.repo.HistoryRepo;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.repo.RoomRepo;
import com.purple.hello.util.DateUtils;
import com.querydsl.core.Fetchable;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class HistoryDAOImpl implements HistoryDAO {
    @PersistenceContext
    EntityManager em;
    @Autowired
    private HistoryRepo historyRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private QuestionRepo questionRepo;
    private final QHistory qHistory = QHistory.history;
    private final QQuestion qQuestion = QQuestion.question;
    private final QUser qUser = QUser.user;
    private final QUserRoom qUserRoom = QUserRoom.userRoom;
    private final QRoom qRoom = QRoom.room;
    private final QFeed qFeed = QFeed.feed;

    public HistoryDAOImpl(HistoryRepo historyRepo, RoomRepo roomRepo, QuestionRepo questionRepo) {
        this.historyRepo = historyRepo;
        this.roomRepo = roomRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public List<DeviceWithQuestionDTO> readDevicesWithDailyQuestionByBeginTime(int beginTime) throws Exception {
        DateTemplate historyCreateAtKST = Expressions.dateTemplate(Date.class, "ADDDATE({0},{1})", qHistory.createAt, "HOUR(9)");
        StringTemplate historyCreateAt = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", historyCreateAtKST, ConstantImpl.create("%Y-%m-%d"));
        Date currentDateKST = DateUtils.addHours(new Date(), 9);
        String currentDateString = DateUtils.format(currentDateKST, "yyyy-MM-dd");
        return new JPAQuery<>(em)
                .select(Projections.constructor(DeviceWithQuestionDTO.class, qRoom.roomId, qUserRoom.roomName, qUser.deviceToken, qHistory.question.content))
                .distinct()
                .from(qUser)
                .join(qUserRoom)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qRoom)
                .on(qUserRoom.room.roomId.eq(qRoom.roomId))
                .join(qHistory)
                .on(qRoom.roomId.eq(qHistory.room.roomId))
                .where(qRoom.beginTime.eq(beginTime))
                .where(historyCreateAt.eq(currentDateString))
                .where(qUser.deviceToken.isNotNull())
                .fetch();
    }

    @Override
    public List<DeviceWithQuestionDTO> readDevicesNotUploadedByBeginTime(int beginTime) throws Exception {
        DateTemplate historyCreateAtKST = Expressions.dateTemplate(Date.class, "ADDDATE({0},{1})", qHistory.createAt, "HOUR(9)");
        StringTemplate historyCreateAt = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", historyCreateAtKST, ConstantImpl.create("%Y-%m-%d"));
        DateTemplate feedCreateAtKST = Expressions.dateTemplate(Date.class, "ADDDATE({0},{1})", qFeed.createAt, "HOUR(9)");
        StringTemplate feedCreateAt = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", feedCreateAtKST, ConstantImpl.create("%Y-%m-%d"));
        Date currentDateKST = DateUtils.addHours(new Date(), 9);
        String currentDateString = DateUtils.format(currentDateKST, "yyyy-MM-dd");
        return new JPAQuery<>(em)
                .select(Projections.constructor(DeviceWithQuestionDTO.class, qRoom.roomId, qUserRoom.roomName, qUser.deviceToken, qHistory.question.content))
                .distinct()
                .from(qUser)
                .join(qUserRoom)
                .on(qUser.userId.eq(qUserRoom.user.userId))
                .join(qRoom)
                .on(qUserRoom.room.roomId.eq(qRoom.roomId))
                .join(qHistory)
                .on(qRoom.roomId.eq(qHistory.room.roomId))
                .where(qRoom.beginTime.eq(beginTime))
                .where(historyCreateAt.eq(currentDateString))
                .where(qUser.deviceToken.isNotNull())
                .where(qUserRoom.userRoomId.notIn(new JPAQuery<>(em)
                        .select(qUserRoom.userRoomId)
                        .from(qUser)
                        .join(qUserRoom)
                        .on(qUser.userId.eq(qUserRoom.user.userId))
                        .join(qFeed)
                        .on(qUserRoom.userRoomId.eq(qFeed.userRoom.userRoomId))
                        .where(feedCreateAt.eq(currentDateString))
                        .fetch())
                )
                .fetch();
    }

    @Override
    public void createFirstHistory(Long roomId) {
        Room room = roomRepo.findById(roomId).get();
        Question question = questionRepo.findById(1L).get();
        History history = History.builder()
                .room(room)
                .question(question)
                .createAt(new Date())
                .build();
        historyRepo.save(history);
    }

    @Override
    public ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException {
        DateTemplate historyCreateAtKST = Expressions.dateTemplate(Date.class, "ADDDATE({0},{1})", qHistory.createAt, "HOUR(9)");
        StringTemplate historyCreateAt = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", historyCreateAtKST, ConstantImpl.create("%Y-%m-%d"));
        Date currentDateKST = DateUtils.addHours(new Date(), 9);
        String currentDateString = DateUtils.format(currentDateKST, "yyyy-MM-dd");
        return new JPAQuery<>(em)
                .select(Projections.constructor(ReadQuestionOutDTO.class, qHistory.question.questionId, qHistory.question.content))
                .from(qHistory)
                .join(qQuestion)
                .on(qHistory.question.questionId.eq(qQuestion.questionId))
                .where(qHistory.room.roomId.eq(roomId))
                .where(historyCreateAt.eq(currentDateString))
                .fetchOne();
    }
}
