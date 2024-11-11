package ru.practicum.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) для создания новой компиляции событий.
 * <p>
 * Этот класс используется для передачи данных о новой компиляции, включая список
 * идентификаторов событий, статус закрепления и заголовок компиляции.
 */
@Getter
@Setter
public class NewCompilationDto {

    private List<Long> events;
    private Boolean pinned;
    @NotBlank(message = "Поле title не может быть пустым")
    @Size(min = 1, max = 50, message = "Поле title должно содержать от 1 до 50 символов")
    private String title;
}
