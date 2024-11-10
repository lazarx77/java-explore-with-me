package ru.practicum.category.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тестовый класс для проверки функциональности репозитория CategoryRepository.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoryRepositoryIT {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category1;
    private Category category2;
    private User user;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();

        user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");

        category1 = new Category();
        category1.setName("Category 1");
        category1 = categoryRepository.save(category1);

        category2 = new Category();
        category2.setName("Category 2");
        category2 = categoryRepository.save(category2);
    }

    @Test
    void findByName_whenCategoryExists_thenCategoryReturned() {
        Optional<Category> foundCategory = categoryRepository.findByName("Category 1");

        assertEquals(category1.getId(), foundCategory.get().getId());
        assertEquals(category1.getName(), foundCategory.get().getName());
    }

    @Test
    void findByName_whenCategoryDoesNotExist_thenNullReturned() {
        Optional<Category> foundCategory = categoryRepository.findByName("Nonexistent Category");

        assertNull(foundCategory.orElse(null));
    }

    @Test
    void findAllCategories_withLimitAndOffset_thenCategoriesReturned() {
        List<Category> categories = categoryRepository.findAllCategories(2, 0);

        assertEquals(2, categories.size());
        assertEquals(category1.getName(), categories.get(0).getName());
        assertEquals(category2.getName(), categories.get(1).getName());
    }

    @Test
    void findAllCategories_withLimitAndOffset_whenNoCategories_thenEmptyListReturned() {
        categoryRepository.deleteAll();

        List<Category> categories = categoryRepository.findAllCategories(2, 0);

        assertEquals(0, categories.size());
    }

    @Test
    void saveCategory_withValidData_thenCategorySaved() {
        Category newCategory = new Category();
        newCategory.setName("New Category");
        Category savedCategory = categoryRepository.save(newCategory);

        assertEquals(newCategory.getName(), savedCategory.getName());
        assertEquals(newCategory.getId(), savedCategory.getId());
    }

    @Test
    void saveCategory_withDuplicateName_thenExceptionThrown() {
        Category duplicateCategory = new Category();
        duplicateCategory.setName("Category 1");

        assertThrows(DataIntegrityViolationException.class, () -> {
            categoryRepository.save(duplicateCategory);
            categoryRepository.flush();
        });
    }
}
