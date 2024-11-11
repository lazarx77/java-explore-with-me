package ru.practicum.category.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый класс для CategoryDtoMapper.
 */
public class CategoryDtoMapperTest {

    @Test
    public void testToCategoryDto() {
        Category category = Mockito.mock(Category.class);
        Mockito.when(category.getId()).thenReturn(1L);
        Mockito.when(category.getName()).thenReturn("Test Category");

        CategoryDto categoryDto = CategoryDtoMapper.toCategoryDto(category);

        assertEquals(1L, categoryDto.getId());
        assertEquals("Test Category", categoryDto.getName());
    }
}
