package com.purple.hello.dto.in;

import com.purple.hello.enu.FeedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedInDTO {
    MultipartFile feedFile;
    String feedUrl;
    String content;
    FeedType feedType;
    long userRoomId;
}
