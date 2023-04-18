package com.purple.hello.dao;

import com.purple.hello.dto.in.CreateFeedInDTO;
import com.purple.hello.dto.out.CompareFeedByRoomIdOutDTO;
import com.purple.hello.dto.out.CreateFeedOutDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface FeedDAO {
    List<CompareFeedByRoomIdOutDTO> compareFeedByRoomId(long roomId, Date date);
    CreateFeedOutDTO createFeedByUserRoomId(CreateFeedInDTO createFeedInDTO);
}
