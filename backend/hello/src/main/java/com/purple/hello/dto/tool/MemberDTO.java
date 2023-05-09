package com.purple.hello.dto.tool;

import com.purple.hello.enu.UserRoomRole;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class MemberDTO {
    long userId;
    String name;
    String profileUrl;
    UserRoomRole userRoomRole;

    @Builder
    public MemberDTO(long userId, String name, String profileUrl, UserRoomRole userRoomRole) {
        this.userId = userId;
        this.name = name;
        this.profileUrl = profileUrl;
        this.userRoomRole = userRoomRole;
    }
}
