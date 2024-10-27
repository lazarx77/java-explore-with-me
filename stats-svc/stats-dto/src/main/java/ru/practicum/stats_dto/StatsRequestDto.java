package ru.practicum.stats_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatsRequestDto {

    private String app;
    private String uri;
    private String ip;
    private String timestamp;

}
