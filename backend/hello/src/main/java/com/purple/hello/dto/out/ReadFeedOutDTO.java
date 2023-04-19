package com.purple.hello.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadFeedOutDTO {
    long feedId;
    String feedUrl;
    String content;
    long userId;
    Date createAt;
}
