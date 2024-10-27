package ru.practicum.stats_client;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor
@Slf4j
public class StatsClientController {

    StatsClient statsClient;

    @PostMapping
    public ResponseEntity<Object> addStats(HttpServletRequest request) {
//        log.info("Сохранение нового пользователя в БД с email {}", dto.getEmail());
        return statsClient.addStats(request);
    }
}
