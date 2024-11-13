package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) для категории.
 * <p>
 * Этот класс используется для передачи данных о категории между слоями приложения.
 * Он содержит информацию о категории, включая её идентификатор и название .
 */
@Getter
@Setter
public class CategoryDto {

    private Long id;
    @NotBlank(message = "Название категории не может быть пустым")
    @Size(min = 1, max = 50)
    private String name;
}
