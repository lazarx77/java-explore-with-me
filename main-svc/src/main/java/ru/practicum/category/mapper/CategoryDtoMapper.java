package ru.practicum.category.mapper;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

/**
 * Класс для преобразования объектов категории в объекты CategoryDto.
 * <p>
 * Этот класс содержит статические методы, которые позволяют конвертировать
 * сущности модели категории в DTO, используемые для передачи данных.
 */
@Slf4j
public class CategoryDtoMapper {

    /**
     * Преобразует объект категории в объект CategoryDto.
     *
     * @param category объект категории, который необходимо преобразовать.
     * @return объект CategoryDto, содержащий данные из переданного объекта категории.
     */
    public static CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
