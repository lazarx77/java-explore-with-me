package ru.practicum.stats_server.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats_dto.StatsRequestDto;
import ru.practicum.stats_dto.StatsResponseDto;
import ru.practicum.stats_server.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для обработки статистики запросов.
 */
@Transactional(readOnly = true)
public interface StatsServerService {

    /**
     * Добавляет статистику на основе предоставленных данных.
     *
     * @param dto объект StatsRequestDto с данными о запросе.
     * @return объект Stats, представляющий добавленную статистику.
     */
    @Transactional
    Stats addStats(StatsRequestDto dto);

    /**
     * Получает статистику за указанный период.
     *
     * @param start  начальная дата и время.
     * @param end    конечная дата и время.
     * @param uris   массив URI (может быть null).
     * @param unique флаг уникальности.
     * @return список объектов StatsResponseDto с данными статистики.
     */
    List<StatsResponseDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}
