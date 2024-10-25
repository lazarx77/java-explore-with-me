package ru.practicum.stats_dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsRequestDto {

    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;

}
