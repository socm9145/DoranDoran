package com.purple.hello.dto.tool;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class MemberDTO {
    long userId;
    String name;
    String profileUrl;

    @Builder
    public MemberDTO(long userId, String name, String profileUrl) {
        this.userId = userId;
        this.name = name;
        this.profileUrl = profileUrl;
    }
}
