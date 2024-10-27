package ru.practicum.stats_server;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats_dto.StatsRequestDto;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StatsServerServiceImpl implements StatsServerService {

    StatsRepository statsRepository;

    @Override
    public StatsRequestDto addStats(HttpServletRequest request) {
        Stats stats = new Stats();
        stats.setTimestamp(LocalDateTime.now());
        stats.setApp(request.getHeader("App"));
        stats.setIp(request.getRemoteAddr());
        stats.setUri(request.getRequestURI());
        statsRepository.save(stats);

        return StatsRequestDto.builder()
                .timestamp(stats.getTimestamp().toString())
                .app(stats.getApp())
                .uri(stats.getUri())
                .ip(stats.getIp())
                .build();
    }
}
