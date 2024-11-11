package ru.practicum.category.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

/**
 * Сервис для работы с категориями.
 * <p>
 * Этот интерфейс определяет методы для создания, обновления, удаления и получения категорий.
 * Все методы, кроме методов получения, помечены аннотацией @Transactional, что означает,
 * что они будут выполняться в рамках транзакции.
 */
@Transactional(readOnly = true)
public interface CategoryService {

    /**
     * Создает новую категорию.
     *
     * @param dto объект NewCategoryDto, содержащий данные для создания категории.
     * @return CategoryDto - созданная категория с её данными.
     */
    @Transactional
    CategoryDto createNewCategory(NewCategoryDto dto);

    /**
     * Обновляет существующую категорию.
     *
     * @param catId идентификатор категории, которую необходимо обновить.
     * @param dto   объект CategoryDto, содержащий обновленные данные категории.
     * @return CategoryDto - обновленная категория с её данными.
     */
    @Transactional
    CategoryDto updateCategory(Long catId, CategoryDto dto);

    /**
     * Удаляет категорию по её идентификатору.
     *
     * @param catId идентификатор категории, которую необходимо удалить.
     */
    @Transactional
    void deleteCategory(Long catId);

    /**
     * Получает список категорий с поддержкой пагинации.
     *
     * @param from индекс, с которого начинается выборка категорий.
     * @param size максимальное количество категорий, которые необходимо вернуть.
     * @return List<CategoryDto> - список категорий.
     */
    List<CategoryDto> getCategories(int from, int size);

    /**
     * Получает категорию по её идентификатору.
     *
     * @param catId идентификатор категории, которую необходимо получить.
     * @return CategoryDto - категория с указанным идентификатором.
     */
    CategoryDto getCategory(Long catId);
}
