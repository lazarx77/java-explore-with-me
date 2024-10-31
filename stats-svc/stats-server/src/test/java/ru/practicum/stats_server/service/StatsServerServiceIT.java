package ru.practicum.stats_server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_dto.StatsResponseDto;
import ru.practicum.stats_server.model.Stats;
import ru.practicum.stats_server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Интеграционный тестовый класс для проверки функциональности сервиса StatsServerService.
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class StatsServerServiceIT {

    @Autowired
    private StatsServerServiceImpl statsServerService;

    @Autowired
    private StatsRepository statsRepository;

    private StatsRequestDto statsRequestDto;

    @BeforeEach
    void setUp() {
        statsRepository.deleteAll();
        statsRequestDto = new StatsRequestDto("TestApp", "/test/uri", "192.168.1.1", LocalDateTime.now());
    }

    @Test
    void addStats_whenValidDto_statsIsSaved() {
        Stats savedStats = statsServerService.addStats(statsRequestDto);
        assertThat(savedStats).isNotNull();
        assertThat(savedStats.getId()).isNotNull();
        assertThat(savedStats.getApp()).isEqualTo("TestApp");
        assertThat(savedStats.getUri()).isEqualTo("/test/uri");
        assertThat(savedStats.getIp()).isEqualTo("192.168.1.1");

        List<Stats> statsList = statsRepository.findAll();
        assertThat(statsList).hasSize(1);
        assertThat(statsList.getFirst().getUri()).isEqualTo(savedStats.getUri());
        assertThat(statsList.getFirst().getIp()).isEqualTo(savedStats.getIp());
        assertThat(statsList.getFirst().getApp()).isEqualTo(savedStats.getApp());
    }

    @Test
    void getStats_whenNoUrisAndNotUnique_returnsStatsSummary() {
        statsServerService.addStats(statsRequestDto);
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        List<StatsResponseDto> statsSummary = statsServerService.getStats(start, end, null, false);

        assertThat(statsSummary).hasSize(1);
        assertThat(statsSummary.getFirst().getApp()).isEqualTo("TestApp");
        assertThat(statsSummary.getFirst().getUri()).isEqualTo("/test/uri");
        assertThat(statsSummary.getFirst().getHits()).isEqualTo(1L);
    }

    @Test
    void getStats_whenUniqueAndUrisProvided_returnsUniqueIpStatsSummary() {
        statsServerService.addStats(statsRequestDto);
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        String[] uris = {"/test/uri"};

        List<StatsResponseDto> uniqueStatsSummary = statsServerService.getStats(start, end, uris, true);

        assertThat(uniqueStatsSummary).hasSize(1);
        assertThat(uniqueStatsSummary.getFirst().getApp()).isEqualTo("TestApp");
        assertThat(uniqueStatsSummary.getFirst().getUri()).isEqualTo("/test/uri");
        assertThat(uniqueStatsSummary.getFirst().getHits()).isEqualTo(1L);
    }

    @Test
    void getStats_whenUrisProvidedAndNotUnique_returnsFilteredStatsSummary() {
        statsServerService.addStats(statsRequestDto);
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        String[] uris = {"/test/uri"};

        List<StatsResponseDto> filteredStatsSummary = statsServerService.getStats(start, end, uris, false);

        assertThat(filteredStatsSummary).hasSize(1);
        assertThat(filteredStatsSummary.getFirst().getApp()).isEqualTo("TestApp");
        assertThat(filteredStatsSummary.getFirst().getUri()).isEqualTo("/test/uri");
        assertThat(filteredStatsSummary.getFirst().getHits()).isEqualTo(1L);
    }
}
