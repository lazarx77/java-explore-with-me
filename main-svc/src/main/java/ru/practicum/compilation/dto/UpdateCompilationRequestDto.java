package ru.practicum.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateCompilationRequestDto {

    private List<Long> events;
    @Size(min = 1, max = 50, message = "Поле title должно содержать от 1 до 50 символов")
    private String title;
    private Boolean pinned;
}
