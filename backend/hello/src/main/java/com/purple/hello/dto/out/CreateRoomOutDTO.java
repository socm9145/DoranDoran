package com.purple.hello.dto.out;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class CreateRoomOutDTO {
    long roomId;
    @Builder
    public CreateRoomOutDTO(long roomId) {
        this.roomId = roomId;
    }
}
