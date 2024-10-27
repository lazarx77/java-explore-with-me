package ru.practicum.stats_server;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats_dto.StatsRequestDto;

@RestController
@RequestMapping(path = "")
@AllArgsConstructor
@Slf4j
public class StatsServerController {

    private final StatsServerService statsServerService;

    @PostMapping("/hit")
    public StatsRequestDto addStats(HttpServletRequest request) {
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        log.info("User-Agent: {}", request.getHeader("User-Agent"));
        return statsServerService.addStats(request);
    }
}
