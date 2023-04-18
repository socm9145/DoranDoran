package com.purple.hello.service.impl;

import com.purple.hello.dao.FeedDAO;
import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;
import com.purple.hello.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {
    @Autowired
    private final FeedDAO feedDAO;
    public FeedServiceImpl(FeedDAO feedDAO){
        this.feedDAO = feedDAO;
    }


    @Override
    public List<CompareFeedByRoomIdOutDTO> compareFeedByRoomId(long roomId, Date date) {
        return this.feedDAO.compareFeedByRoomId(roomId, date);
    }

    @Override
    public CreateFeedOutDTO createFeedByUserRoomId(CreateFeedInDTO createFeedInDTO) {
        return this.feedDAO.createFeedByUserRoomId(createFeedInDTO);
    }
}
