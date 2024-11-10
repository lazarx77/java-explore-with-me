package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) для создания новой категории.
 * <p>
 * Этот класс используется для передачи данных о новой категории при её создании.
 * Он содержит только информацию о названии категории, так как идентификатор
 * будет сгенерирован автоматически при сохранении в базе данных.
 */
@Getter
@Setter
public class NewCategoryDto {

    @NotBlank(message = "Название категории не может быть пустым")
    @Size(min = 1, max = 50)
    private String name;
}
