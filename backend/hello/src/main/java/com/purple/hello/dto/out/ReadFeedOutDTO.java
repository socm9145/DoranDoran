package com.purple.hello.dto.out;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReadFeedOutDTO {
    long feedId;
    String feedUrl;
    String content;
    long userId;
    Date createAt;

    @Builder
    public ReadFeedOutDTO(long feedId, String feedUrl, String content, long userId, Date createAt) {
        this.feedId = feedId;
        this.feedUrl = feedUrl;
        this.content = content;
        this.userId = userId;
        this.createAt = createAt;
    }
}
