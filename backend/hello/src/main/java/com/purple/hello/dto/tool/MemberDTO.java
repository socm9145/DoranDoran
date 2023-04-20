package com.purple.hello.dto.tool;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class MemberDTO {
    String name;
    String profileUrl;

    @Builder
    public MemberDTO(String name, String profileUrl) {
        this.name = name;
        this.profileUrl = profileUrl;
    }
}
