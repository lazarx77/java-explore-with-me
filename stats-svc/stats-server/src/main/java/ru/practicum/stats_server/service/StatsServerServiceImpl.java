package ru.practicum.stats_server.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_dto.StatsResponseDto;
import ru.practicum.stats_server.model.Stats;
import ru.practicum.stats_server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Реализация сервиса для обработки статистики запросов.
 */
@Service
@AllArgsConstructor
@Slf4j
public class StatsServerServiceImpl implements StatsServerService {

    StatsRepository statsRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Stats addStats(StatsRequestDto dto) {
        Stats stats = new Stats();
        stats.setTimestamp(dto.getTimestamp());
        stats.setApp(dto.getApp());
        stats.setIp(dto.getIp());
        stats.setUri(dto.getUri());

        return statsRepository.save(stats);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatsResponseDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        log.info("Вызывается метод getStats в сервисе StatsServerServiceImpl");
        log.info("start: {}", start);
        log.info("end: {}", end);
        log.info("uris: {}", (Object) uris);
        log.info("unique: {}", unique);
        List<StatsResponseDto> stats = new ArrayList<>();

        List<String> urisList = (uris != null) ? Arrays.asList(uris) : null;

        if (urisList == null && !unique) {
            stats = statsRepository.findStatsSummaryBetween(start, end, null);
        } else if (urisList == null) {
            stats = statsRepository.findUniqueIpStatsSummaryBetween(start, end, null);
        } else if (!urisList.isEmpty() && !unique) {
            stats = statsRepository.findStatsSummaryBetween(start, end, urisList);
        } else if (!urisList.isEmpty()) {
            stats = statsRepository.findUniqueIpStatsSummaryBetween(start, end, urisList);
        }
        stats.sort((s1, s2) -> Long.compare(s2.getHits(), s1.getHits()));
        return stats;
    }
}
