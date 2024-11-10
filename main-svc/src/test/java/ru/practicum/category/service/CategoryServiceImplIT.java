package ru.practicum.category.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.CategoryNameDoubleException;
import ru.practicum.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Интеграционный тестовый класс для проверки функциональности сервиса CategoryServiceImpl.
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class CategoryServiceImplIT {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private NewCategoryDto newCategoryDto;

    @BeforeEach
    void setUp() {
        newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("Test Category");
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void createNewCategory_whenCategoryIsValid_categoryIsSaved() {
        CategoryDto savedCategoryDto = categoryService.createNewCategory(newCategoryDto);
        assertThat(savedCategoryDto).isNotNull();
        assertThat(savedCategoryDto.getName()).isEqualTo("Test Category");
    }

    @Test
    void getCategories_whenCategoriesExist_categoriesAreReturned() {
        categoryService.createNewCategory(newCategoryDto);
        List<CategoryDto> categories = categoryService.getCategories(0, 10);
        assertThat(categories).hasSize(1);
    }

    @Test
    void updateCategory_whenCategoryExists_categoryIsUpdated() {
        CategoryDto savedCategoryDto = categoryService.createNewCategory(newCategoryDto);
        Long catId = savedCategoryDto.getId();

        CategoryDto updatedCategoryDto = new CategoryDto();
        updatedCategoryDto.setName("Updated Category");
        CategoryDto result = categoryService.updateCategory(catId, updatedCategoryDto);

        assertThat(result.getName()).isEqualTo("Updated Category");
    }

    @Test
    void deleteCategory_whenCategoryExists_categoryIsDeleted() {
        CategoryDto addedCategoryDto = categoryService.createNewCategory(newCategoryDto);
        categoryService.deleteCategory(addedCategoryDto.getId());

        List<CategoryDto> categories = categoryService.getCategories(0, 10);
        assertThat(categories).isEmpty();
    }

    @Test
    void createNewCategory_whenCategoryNameIsNotUnique_throwsCategoryNameDoubleException() {
        categoryService.createNewCategory(newCategoryDto);
        NewCategoryDto anotherCategoryDto = new NewCategoryDto();
        anotherCategoryDto.setName("Test Category");

        assertThrows(CategoryNameDoubleException.class, () -> categoryService.createNewCategory(anotherCategoryDto));
    }

    @Test
    void getCategory_whenCategoryExists_categoryIsReturned() {
        CategoryDto addedCategoryDto = categoryService.createNewCategory(newCategoryDto);
        CategoryDto foundCategoryDto = categoryService.getCategory(addedCategoryDto.getId());

        assertThat(foundCategoryDto).isNotNull();
        assertThat(foundCategoryDto.getName()).isEqualTo("Test Category");
    }

    @Test
    void getCategory_whenCategoryNotFound_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> categoryService.getCategory(999L));
    }
}
