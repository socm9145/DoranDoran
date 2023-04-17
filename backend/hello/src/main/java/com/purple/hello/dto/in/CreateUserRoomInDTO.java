package com.purple.hello.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRoomInDTO {
    String roomCode;
    String roomName;
    String userName;
    String roomPassword;
    String roomQuestion;
}
