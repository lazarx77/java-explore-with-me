package ru.practicum.category.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

@Transactional(readOnly = true)
public interface CategoryService {

    @Transactional
    CategoryDto createNewCategory(NewCategoryDto dto);

    @Transactional
    CategoryDto updateCategory(Long catId, CategoryDto dto);

    @Transactional
    void deleteCategory(Long catId);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(Long catId);
}