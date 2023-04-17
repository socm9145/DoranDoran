package com.purple.hello.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadRoomOutDTO {
    String groupName;
    List<String> userNames;
    List<String> userProfileUrls;
}
