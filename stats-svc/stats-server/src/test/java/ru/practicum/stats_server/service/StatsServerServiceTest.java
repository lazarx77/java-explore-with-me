package ru.practicum.stats_server.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_dto.StatsResponseDto;
import ru.practicum.stats_server.model.Stats;
import ru.practicum.stats_server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для сервиса статистики.
 */
@ExtendWith(MockitoExtension.class)
class StatsServerServiceTest {

    @Mock
    private StatsRepository statsRepository;

    @InjectMocks
    private StatsServerServiceImpl statsServerService;

    @Test
    void addStats_whenValidDto_thenStatsSaved() {
        StatsRequestDto dto = new StatsRequestDto("TestApp", "/test/uri", "192.168.1.1",
                LocalDateTime.now());
        Stats savedStats = new Stats();
        savedStats.setApp(dto.getApp());
        savedStats.setUri(dto.getUri());
        savedStats.setIp(dto.getIp());
        savedStats.setTimestamp(dto.getTimestamp());

        when(statsRepository.save(any(Stats.class))).thenReturn(savedStats);

        Stats actualStats = statsServerService.addStats(dto);

        assertEquals(savedStats, actualStats);
        verify(statsRepository, times(1)).save(any(Stats.class));
    }

    @Test
    void getStats_whenNoUrisAndNotUnique_thenReturnStatsSummary() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        StatsResponseDto responseDto = new StatsResponseDto("TestApp", "/test/uri", 5L);
        when(statsRepository.findStatsSummaryBetween(start, end, null))
                .thenReturn(new ArrayList<>(List.of(responseDto)));
        List<StatsResponseDto> actualStats = statsServerService.getStats(start, end, null, false);

        assertEquals(1, actualStats.size());
        assertEquals(responseDto, actualStats.get(0)); // Изменено на get(0)
        verify(statsRepository, times(1)).findStatsSummaryBetween(start, end, null);
    }

    @Test
    void getStats_whenUniqueAndUrisProvided_thenReturnUniqueIpStatsSummary() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        String[] uris = {"/test/uri"};
        StatsResponseDto responseDto = new StatsResponseDto("TestApp", "/test/uri", 3L);
        when(statsRepository.findUniqueIpStatsSummaryBetween(start, end, List.of(uris)))
                .thenReturn(new ArrayList<>(List.of(responseDto)));

        List<StatsResponseDto> actualStats = statsServerService.getStats(start, end, uris, true);

        assertEquals(1, actualStats.size());
        assertEquals(responseDto, actualStats.get(0)); // Изменено на get(0)
        verify(statsRepository, times(1))
                .findUniqueIpStatsSummaryBetween(start, end, List.of(uris));
    }

    @Test
    void getStats_whenUrisProvidedAndNotUnique_thenReturnFilteredStatsSummary() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        String[] uris = {"/test/uri"};
        StatsResponseDto responseDto = new StatsResponseDto("TestApp", "/test/uri", 4L);
        when(statsRepository.findStatsSummaryBetween(start, end, List.of(uris)))
                .thenReturn(new ArrayList<>(List.of(responseDto)));

        List<StatsResponseDto> actualStats = statsServerService.getStats(start, end, uris, false);

        assertEquals(1, actualStats.size());
        assertEquals(responseDto, actualStats.get(0)); // Изменено на get(0)
        verify(statsRepository, times(1)).findStatsSummaryBetween(start, end, List.of(uris));
    }
}
