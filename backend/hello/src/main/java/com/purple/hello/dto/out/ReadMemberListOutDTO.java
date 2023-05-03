package com.purple.hello.dto.out;

import com.purple.hello.dto.tool.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadMemberListOutDTO {
    long roomId;
    List<MemberDTO> members;
}
