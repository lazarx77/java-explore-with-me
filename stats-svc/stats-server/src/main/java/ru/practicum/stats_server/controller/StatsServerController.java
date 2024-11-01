package ru.practicum.stats_server.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_dto.StatsResponseDto;
import ru.practicum.stats_server.mapper.StatsDtoMapper;
import ru.practicum.stats_server.service.StatsServerService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.practicum.stats_server.service.DateTimeUtil.DATE_TIME_FORMAT;

/**
 * Контроллер для обработки запросов статистики.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping
@AllArgsConstructor
@Slf4j
public class StatsServerController {

    private final StatsServerService statsServerService;

    /**
     * Добавляет статистику о запросе.
     *
     * @param dto объект StatsRequestDto с данными о запросе.
     * @return объект StatsRequestDto с добавленной статистикой.
     */
    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatsRequestDto addStats(@RequestBody @Validated StatsRequestDto dto) {
        log.info("Вызывается метод addStats в контроллере StatsServerController");
        log.info("client ip: {}", dto.getIp());
        log.info("endpoint path: {}", dto.getUri());
        log.info("User-Agent: {}", dto.getApp());
        log.info("timestamp: {}", dto.getTimestamp());

        return StatsDtoMapper.mapToStatsRequestDto(statsServerService.addStats(dto));
    }

    /**
     * Получает статистику за указанный период.
     *
     * @param start  начальная дата и время.
     * @param end    конечная дата и время.
     * @param uris   массив URI (может быть null).
     * @param unique флаг уникальности.
     * @return список объектов StatsResponseDto с данными статистики.
     */
    @GetMapping(path = "/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<StatsResponseDto> getStats(@RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                           LocalDateTime start,
                                           @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end,
                                           @RequestParam(required = false) String[] uris,
                                           @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Вызывается метод getStats в контроллере StatsServerController");
        log.info("start: {}", start);
        log.info("end: {}", end);
        log.info("uris: {}", Arrays.toString(uris));
        log.info("unique: {}", unique);
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Время 'end' должно быть позже времени 'start'.");
        }
        return statsServerService.getStats(start, end, uris, unique);
    }
}
