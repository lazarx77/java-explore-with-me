package ru.practicum.stats_server.mapper;

import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_server.model.Stats;

/**
 * Mapper для преобразования объектов Stats в StatsRequestDto.
 */
public class StatsDtoMapper {

    /**
     * Преобразует объект Stats в StatsRequestDto.
     *
     * @param stats объект Stats, который нужно преобразовать.
     * @return преобразованный объект StatsRequestDto.
     */
    public static StatsRequestDto mapToStatsRequestDto(Stats stats) {
        StatsRequestDto statsRequestDto = new StatsRequestDto();
        statsRequestDto.setApp(stats.getApp());
        statsRequestDto.setUri(stats.getUri());
        statsRequestDto.setIp(stats.getIp());
        statsRequestDto.setTimestamp(stats.getTimestamp());
        return statsRequestDto;
    }
}
