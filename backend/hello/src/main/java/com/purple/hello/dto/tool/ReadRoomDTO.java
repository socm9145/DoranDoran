package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ReadRoomDTO {
    long userId;
    long userRoomId;
    long roomId;
    String roomName;
    String userName;
    String userProfileUrl;

    @Builder
    public ReadRoomDTO(long userRoomId, long roomId, String roomName, String userName, String userProfileUrl, long userId) {
        this.userId = userId;
        this.userRoomId = userRoomId;
        this.roomId = roomId;
        this.roomName = roomName;
        this.userName = userName;
        this.userProfileUrl = userProfileUrl;
    }
}
