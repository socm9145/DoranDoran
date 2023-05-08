package com.purple.hello.dao;

import com.purple.hello.dto.out.ReadQuestionOutDTO;

public interface QuestionDAO {
    ReadQuestionOutDTO readQuestionByQuestionId(long questionId) throws Exception;
}
