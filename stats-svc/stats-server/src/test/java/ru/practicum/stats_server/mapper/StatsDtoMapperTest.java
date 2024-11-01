package ru.practicum.stats_server.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_server.model.Stats;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый класс для маппера.
 */
class StatsDtoMapperTest {

    @Test
    void mapToStatsRequestDto_shouldMapStatsToStatsRequestDto() {
        Stats stats = new Stats();
        stats.setApp("TestApp");
        stats.setUri("/api/test");
        stats.setIp("192.168.0.1");
        stats.setTimestamp(LocalDateTime.now());

        StatsRequestDto result = StatsDtoMapper.mapToStatsRequestDto(stats);

        assertEquals(stats.getApp(), result.getApp());
        assertEquals(stats.getUri(), result.getUri());
        assertEquals(stats.getIp(), result.getIp());
        assertEquals(stats.getTimestamp(), result.getTimestamp());
    }
}
