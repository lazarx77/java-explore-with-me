package ru.practicum.stats_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для ответа с данными статистики.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsResponseDto {

    private String app;
    private String uri;
    private Long hits;
}
