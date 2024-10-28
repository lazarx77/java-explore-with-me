package ru.practicum.stats_server.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_server.service.StatsServerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "")
@AllArgsConstructor
@Slf4j
public class StatsServerController {

    private final StatsServerService statsServerService;

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatsRequestDto addStats(@RequestBody @Validated StatsRequestDto dto) {
        log.info("Вызывается метод addStats в контроллере StatsServerController");
        log.info("client ip: {}", dto.getIp());
        log.info("endpoint path: {}", dto.getUri());
        log.info("User-Agent: {}", dto.getApp());

        statsServerService.addStats(dto);
        return dto;
    }


}
