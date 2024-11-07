package ru.practicum.compilation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewCompilationDto {

    private List<Long> events;
    private Boolean pinned;
    @NotNull(message = "Поле title не может быть пустым")
    @Min(1)
    @Max(50)
    private String title;
}
