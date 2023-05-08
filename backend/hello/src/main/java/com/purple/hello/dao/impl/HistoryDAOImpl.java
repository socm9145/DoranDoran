package com.purple.hello.dao.impl;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dto.in.CreateQuestionInDTO;
import com.purple.hello.entity.History;
import com.purple.hello.entity.Question;
import com.purple.hello.entity.Room;
import com.purple.hello.repo.HistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HistoryDAOImpl implements HistoryDAO {
    @Autowired
    private HistoryRepo historyRepo;
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
}
