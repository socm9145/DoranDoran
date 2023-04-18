package com.purple.hello.service;

import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface FeedService {
    List<CompareFeedByRoomIdOutDTO> compareFeedByRoomId(long roomId, Date date);
    CreateFeedOutDTO createFeedByUserRoomId(CreateFeedInDTO createFeedInDTO);
}
