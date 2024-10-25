package ru.practicum.stats_server;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats_dto.StatsRequestDto;

@Transactional(readOnly = true)
public interface StatsServerService {

    Stats addStats(StatsRequestDto dto, HttpServletRequest request);
}
