package com.purple.hello.dao.impl;

import com.purple.hello.dao.QuestionDAO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.entity.Question;
import com.purple.hello.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionDAOImpl implements QuestionDAO {
    @Autowired
    private QuestionRepo questionRepo;
    public QuestionDAOImpl(QuestionRepo questionRepo){
        this.questionRepo = questionRepo;
    }
    @Override
    public ReadQuestionOutDTO readQuestionByQuestionId(long questionId) {
        Question question = questionRepo.findByQuestionId(questionId);
        ReadQuestionOutDTO readQuestionOutDTO = new ReadQuestionOutDTO();
        readQuestionOutDTO.setContent(question.getContent());
        return readQuestionOutDTO;
    }
}
