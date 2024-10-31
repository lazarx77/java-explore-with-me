package ru.practicum.stats_server.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.stats_dto.StatsResponseDto;
import ru.practicum.stats_server.model.Stats;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для репозитория.
 */
@DataJpaTest
public class StatsRepositoryIT {

    @Autowired
    private StatsRepository statsRepository;

    private Stats testStat1;
    private Stats testStat2;
    private Stats testStat3;

    @BeforeEach
    public void setUp() {
        testStat1 = new Stats();
        testStat1.setApp("TestApp");
        testStat1.setUri("/test/uri1");
        testStat1.setIp("192.168.1.1");
        testStat1.setTimestamp(LocalDateTime.now().minusHours(1));
        statsRepository.save(testStat1);

        testStat2 = new Stats();
        testStat2.setApp("TestApp");
        testStat2.setUri("/test/uri1");
        testStat2.setIp("192.168.1.2");
        testStat2.setTimestamp(LocalDateTime.now().minusHours(1));
        statsRepository.save(testStat2);

        testStat3 = new Stats();
        testStat3.setApp("TestApp");
        testStat3.setUri("/test/uri2");
        testStat3.setIp("192.168.1.1");
        testStat3.setTimestamp(LocalDateTime.now().minusDays(1));
        statsRepository.save(testStat3);
    }

    @Test
    public void whenFindStatsSummaryBetween_thenReturnStatsSummary() {
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now();
        List<StatsResponseDto> statsSummary = statsRepository.findStatsSummaryBetween(start, end, null);

        assertThat(statsSummary).hasSize(2);
        assertThat(statsSummary).extracting(StatsResponseDto::getApp).containsOnly("TestApp");
        assertThat(statsSummary).extracting(StatsResponseDto::getUri).containsExactlyInAnyOrder("/test/uri1",
                "/test/uri2");
        assertThat(statsSummary).extracting(StatsResponseDto::getHits).containsExactly(2L, 1L);
    }

    @Test
    public void whenFindUniqueIpStatsSummaryBetween_thenReturnUniqueIpStatsSummary() {
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now();
        List<StatsResponseDto> uniqueIpStatsSummary = statsRepository.findUniqueIpStatsSummaryBetween(start, end, null);

        assertThat(uniqueIpStatsSummary).hasSize(2);
        assertThat(uniqueIpStatsSummary).extracting(StatsResponseDto::getApp).containsOnly("TestApp");
        assertThat(uniqueIpStatsSummary).extracting(StatsResponseDto::getUri)
                .containsExactlyInAnyOrder("/test/uri1", "/test/uri2");
        assertThat(uniqueIpStatsSummary).extracting(StatsResponseDto::getHits).containsExactly(2L, 1L);
    }

    @Test
    public void whenFindStatsSummaryBetweenWithUris_thenReturnFilteredStatsSummary() {
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now();
        List<StatsResponseDto> statsSummary = statsRepository.findStatsSummaryBetween(start, end,
                List.of("/test/uri1"));

        assertThat(statsSummary).hasSize(1);
        assertThat(statsSummary).extracting(StatsResponseDto::getUri).containsExactly("/test/uri1");
        assertThat(statsSummary).extracting(StatsResponseDto::getHits).containsExactly(2L);
    }
}
