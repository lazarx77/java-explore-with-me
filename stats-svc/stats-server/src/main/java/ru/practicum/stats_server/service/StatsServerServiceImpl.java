package ru.practicum.stats_server.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_server.model.Stats;
import ru.practicum.stats_server.repository.StatsRepository;

@Service
@AllArgsConstructor
@Slf4j
public class StatsServerServiceImpl implements StatsServerService {

    StatsRepository statsRepository;

    @Override
    public Stats addStats(StatsRequestDto dto) {
        Stats stats = new Stats();
        stats.setTimestamp(dto.getTimestamp());
        stats.setApp(dto.getApp());
        stats.setIp(dto.getIp());
        stats.setUri(dto.getUri());

        return statsRepository.save(stats);
    }
}
