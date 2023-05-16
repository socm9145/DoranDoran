package com.purple.hello.service;

import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.dto.tool.CommonNotificationDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public interface HistoryService {
    ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException, NullPointerException;
    List<CommonNotificationDTO> createNewQuestionNotificationsByBeginTime(int beginTime) throws Exception;
    List<CommonNotificationDTO> createRemindQuestionNotificationsByBeginTime(int beginTime) throws Exception;
    public void createFirstHistory(Long roomId);
}
