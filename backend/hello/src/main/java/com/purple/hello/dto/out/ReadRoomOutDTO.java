package com.purple.hello.dto.out;

import com.purple.hello.dto.tool.MemberDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadRoomOutDTO {
    long userRoomId;
    long roomId;
    String roomName;
    List<MemberDTO> members;


}
