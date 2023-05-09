package com.purple.hello.dao.impl;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dto.in.CreateQuestionInDTO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.entity.*;
import com.purple.hello.repo.HistoryRepo;
import com.purple.hello.repo.QuestionRepo;
import com.purple.hello.repo.RoomRepo;
import org.checkerframework.checker.units.qual.A;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
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

    public HistoryDAOImpl(HistoryRepo historyRepo, RoomRepo roomRepo, QuestionRepo questionRepo) {
        this.historyRepo = historyRepo;
        this.roomRepo = roomRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public boolean createHistory(CreateQuestionInDTO createQuestionInDTO) {
        Room realRoom = roomRepo.findById(createQuestionInDTO.getRoomId()).get();
        Question question = questionRepo.findById(createQuestionInDTO.getNo()).get();

        History history = History.builder()
                .createAt(new Date())
                .question(question)
                .room(realRoom)
                .build();
        historyRepo.save(history);

        return true;
    }

    @Override
    public ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringTemplate dateStringTemplate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})",qHistory.createAt, ConstantImpl.create("%Y-%m-%d"));
        ReadQuestionOutDTO readQuestionOutDTO = new JPAQuery<>(em)
                .select(Projections.constructor(ReadQuestionOutDTO.class, qHistory.question.questionId, qHistory.question.content))
                .from(qHistory)
                .join(qQuestion)
                .on(qHistory.question.questionId.eq(qQuestion.questionId))
                .where(qHistory.room.roomId.eq(roomId))
                .where(dateStringTemplate.eq(simpleDateFormat.format(date)))
                .fetchOne();
        return readQuestionOutDTO;
    }
}
