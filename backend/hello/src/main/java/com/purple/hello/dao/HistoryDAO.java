package com.purple.hello.dao;

import com.purple.hello.dto.in.CreateQuestionInDTO;

public interface HistoryDAO {
    boolean createHistory(CreateQuestionInDTO createQuestionInDTO);
}
