package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HistoryTypePythonDTO {
    Long roomID;
    List<HistoryTypeDTO> types;
    List<HistoryTypeDTO> feeds;
    List<HistoryMinMaxDTO> minMaxCurrent;

    @Builder
    public HistoryTypePythonDTO(Long roomID, List<HistoryTypeDTO> types, List<HistoryTypeDTO> feeds, List<HistoryMinMaxDTO> minMaxCurrent){
        this.roomID = roomID;
        this.feeds = feeds;
        this.types = types;
        this.minMaxCurrent = minMaxCurrent;
    }
}
