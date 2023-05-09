package com.purple.hello.dto.tool;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoryMinMaxDTO {
    int questionId;
    String questionType;
    int min;
    int max;
}
