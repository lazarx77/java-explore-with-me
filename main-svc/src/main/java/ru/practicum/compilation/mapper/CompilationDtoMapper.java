package ru.practicum.compilation.mapper;

import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.mapper.EventDtoMapper;
import ru.practicum.stats_client.client.StatsClient;


/**
 * Класс для преобразования объектов компиляции в объекты CompilationDtoResponse.
 * <p>
 * Этот класс содержит статические методы, которые позволяют конвертировать
 * сущности модели компиляции в DTO, используемые для передачи данных о компиляциях.
 */
public class CompilationDtoMapper {

    /**
     * Преобразует объект компиляции в объект CompilationDtoResponse.
     *
     * @param compilation объект компиляции, который необходимо преобразовать.
     * @param statsClient клиент для получения статистики событий.
     * @return объект CompilationDtoResponse, содержащий данные из переданного объекта компиляции.
     */
    public static CompilationDtoResponse toCompilationDtoResponse(Compilation compilation, StatsClient statsClient) {
        return CompilationDtoResponse.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream()
                        .map(event -> EventDtoMapper.toEventShortDto(event, statsClient))
                        .toList())
                .build();
    }
}
