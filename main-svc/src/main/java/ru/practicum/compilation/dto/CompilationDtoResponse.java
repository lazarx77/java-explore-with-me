package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.dto.event.EventShortDto;

import java.util.List;

/**
 * Data Transfer Object (DTO) для ответа на запрос о компиляции событий.
 * <p>
 * Этот класс используется для передачи данных о компиляции, включая список событий,
 * идентификатор компиляции, статус закрепления и заголовок компиляции.
 */
@Getter
@Setter
@Builder
public class CompilationDtoResponse {

    private List<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
