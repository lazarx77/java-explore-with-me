package ru.practicum.category.mapper;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

@Slf4j
public class CategoryDtoMapper {

    public static CategoryDto toCategoryDto(Category category) {
        log.info("Вызывается метод toCategoryDto в CategoryDtoMapper");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
