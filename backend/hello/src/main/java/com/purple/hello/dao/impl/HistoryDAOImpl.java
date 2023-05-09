package com.purple.hello.dao.impl;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dto.in.CreateQuestionInDTO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.entity.*;
import com.purple.hello.repo.HistoryRepo;
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
    private final QHistory qHistory = QHistory.history;
    private final QQuestion qQuestion = QQuestion.question;
    public HistoryDAOImpl(HistoryRepo historyRepo){
        this.historyRepo = historyRepo;
    }
    @Override
    public boolean createHistory(CreateQuestionInDTO createQuestionInDTO) throws Exception{
        Room room = new Room();
        room.setRoomId(createQuestionInDTO.getRoomId());
        Question question = new Question();
        question.setQuestionId(createQuestionInDTO.getQueryId());

        History history = History.builder()
                .createAt(new Date())
                .question(question)
                .room(room)
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
