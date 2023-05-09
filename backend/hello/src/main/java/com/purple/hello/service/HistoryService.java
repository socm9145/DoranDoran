package com.purple.hello.service;

import com.purple.hello.dto.out.ReadQuestionOutDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public interface HistoryService {
    ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException, NullPointerException;
}
