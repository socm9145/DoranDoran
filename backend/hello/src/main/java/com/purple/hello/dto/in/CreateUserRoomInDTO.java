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
    long userId;
    String userName;
    String roomName;
    String roomPassword;
    String roomQuestion;
}
