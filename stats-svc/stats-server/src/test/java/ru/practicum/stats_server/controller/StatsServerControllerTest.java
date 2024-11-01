package ru.practicum.stats_server.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_dto.StatsResponseDto;
import ru.practicum.stats_server.model.Stats;
import ru.practicum.stats_server.service.StatsServerService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для контроллера StatsServerController.
 */
@ExtendWith(MockitoExtension.class)
class StatsServerControllerTest {

    @Mock
    private StatsServerService statsServerService;

    @InjectMocks
    private StatsServerController statsServerController;

    @Test
    void addStats_whenInvoked_thenStatsRequestDtoReturned() {
        StatsRequestDto statsRequestDto = new StatsRequestDto("192.168.0.1", "/api/test", "TestApp",
                LocalDateTime.now());

        Stats stats = new Stats();
        stats.setIp(statsRequestDto.getIp());
        stats.setUri(statsRequestDto.getUri());
        stats.setApp(statsRequestDto.getApp());
        stats.setTimestamp(statsRequestDto.getTimestamp());

        when(statsServerService.addStats(any(StatsRequestDto.class))).thenReturn(stats);

        StatsRequestDto actualStatsRequestDto = statsServerController.addStats(statsRequestDto);

        assertEquals(statsRequestDto.getIp(), actualStatsRequestDto.getIp());
        assertEquals(statsRequestDto.getUri(), actualStatsRequestDto.getUri());
        assertEquals(statsRequestDto.getApp(), actualStatsRequestDto.getApp());
        assertEquals(statsRequestDto.getTimestamp(), actualStatsRequestDto.getTimestamp());
        verify(statsServerService).addStats(statsRequestDto);
    }

    @Test
    void getStats_whenInvoked_thenStatsResponseDtoListReturned() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        String[] uris = {"/api/test"};
        boolean unique = false;

        StatsResponseDto statsResponseDto = new StatsResponseDto("192.168.0.1", "/api/test", 10L);
        List<StatsResponseDto> expectedStatsResponseDtos = List.of(statsResponseDto);
        when(statsServerService.getStats(any(LocalDateTime.class), any(LocalDateTime.class), any(String[].class),
                any(Boolean.class)))
                .thenReturn(expectedStatsResponseDtos);

        List<StatsResponseDto> actualStatsResponseDtos = statsServerController.getStats(start, end, uris, unique);

        assertEquals(expectedStatsResponseDtos, actualStatsResponseDtos);
    }

    @Test
    void getStats_whenEndBeforeStart_thenIllegalArgumentExceptionThrown() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().minusDays(1);
        String[] uris = {"/api/test"};
        boolean unique = false;

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            statsServerController.getStats(start, end, uris, unique);
        });

        assertEquals("Время 'end' должно быть позже времени 'start'.", exception.getMessage());
    }
}
