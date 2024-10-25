package ru.practicum.stats_dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsResponseDto {

    private String app;
    private String uri;
    private String ip;
}
