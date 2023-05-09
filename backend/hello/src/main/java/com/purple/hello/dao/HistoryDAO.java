package com.purple.hello.dao;

import com.purple.hello.dto.in.CreateQuestionInDTO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;

import java.io.IOException;
import java.util.Date;

public interface HistoryDAO {
    boolean createHistory(CreateQuestionInDTO createQuestionInDTO);
    ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException;
    boolean createHistory(CreateQuestionInDTO createQuestionInDTO) throws Exception;
}
