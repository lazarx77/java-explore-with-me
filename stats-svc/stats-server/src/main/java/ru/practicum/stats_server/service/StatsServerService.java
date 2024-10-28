package ru.practicum.stats_server.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_server.model.Stats;

//@Transactional(readOnly = true)
public interface StatsServerService {

    Stats addStats(StatsRequestDto dto);
}
