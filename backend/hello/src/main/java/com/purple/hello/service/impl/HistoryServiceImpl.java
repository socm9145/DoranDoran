package com.purple.hello.service.impl;

import com.purple.hello.dao.HistoryDAO;
import com.purple.hello.dao.RoomDAO;
import com.purple.hello.dto.out.ReadQuestionOutDTO;
import com.purple.hello.service.HistoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final RoomDAO roomDAO;
    private final HistoryDAO historyDAO;

    public HistoryServiceImpl(RoomDAO roomDAO, HistoryDAO historyDAO) {
        this.roomDAO = roomDAO;
        this.historyDAO = historyDAO;
    }

    @Override
    public ReadQuestionOutDTO readQuestionByRoomIdAndDate(long roomId, Date date) throws IOException, NullPointerException {
        if(date == null) {
            throw new NullPointerException();
        }else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = new Date();
            if(currentDate.compareTo(date) < 0){
                return null;
            }
            if(simpleDateFormat.format(currentDate).equals(simpleDateFormat.format(date))) {
                int beginTime = roomDAO.readBeginTimeByRoomId(roomId);
                int currentHour = LocalDateTime.now().getHour();
                if(currentHour < beginTime){
                    return null;
                }
            }
            return historyDAO.readQuestionByRoomIdAndDate(roomId, date);
        }
    }
}
