package com.purple.hello.service;

import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface FeedService {
    List<CompareFeedByRoomIdOutDTO> compareFeedByRoomId(long roomId, Date date);
}
