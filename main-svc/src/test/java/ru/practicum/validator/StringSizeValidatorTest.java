package ru.practicum.validator;

import org.junit.jupiter.api.Test;
import ru.practicum.exception.StringSizeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности валидатора StringSizeValidator.
 */
class StringSizeValidatorTest {

    @Test
    void validateTitle_whenTitleIsValid_shouldNotThrowException() {
        assertDoesNotThrow(() -> StringSizeValidator.validateTitle("Корректное название"));
    }

    @Test
    void validateTitle_whenTitleIsTooShort_shouldThrowException() {
        assertThrows(StringSizeException.class, () -> StringSizeValidator.validateTitle("аб"));
    }

    @Test
    void validateTitle_whenTitleIsTooLong_shouldThrowException() {
        assertThrows(StringSizeException.class, () -> StringSizeValidator.validateTitle("а".repeat(121)));
    }

    @Test
    void validateDescription_whenDescriptionIsValid_shouldNotThrowException() {
        assertDoesNotThrow(() -> StringSizeValidator.validateDescription("Это корректное описание с достаточной" +
                " длиной."));
    }

    @Test
    void validateDescription_whenDescriptionIsTooShort_shouldThrowException() {
        assertThrows(StringSizeException.class, () -> StringSizeValidator.validateDescription("Короткое описание."));
    }

    @Test
    void validateDescription_whenDescriptionIsTooLong_shouldThrowException() {
        assertThrows(StringSizeException.class, () -> StringSizeValidator.validateDescription("а".repeat(7001)));
    }

    @Test
    void validateAnnotation_whenAnnotationIsValid_shouldNotThrowException() {
        assertDoesNotThrow(() -> StringSizeValidator.validateAnnotation("Это корректная аннотация с достаточной длиной."));
    }

    @Test
    void validateAnnotation_whenAnnotationIsTooShort_shouldThrowException() {
        assertThrows(StringSizeException.class, () -> StringSizeValidator.validateAnnotation("Короткая аннотация."));
    }

    @Test
    void validateAnnotation_whenAnnotationIsTooLong_shouldThrowException() {
        assertThrows(StringSizeException.class, () -> StringSizeValidator.validateAnnotation("а".repeat(2001)));
    }

    @Test
    void validateCategoryName_whenCategoryNameIsValid_shouldNotThrowException() {
        assertDoesNotThrow(() -> StringSizeValidator.validateCategoryName("Корректное название категории"));
    }

    @Test
    void validateCategoryName_whenCategoryNameIsEmpty_shouldThrowException() {
        assertThrows(StringSizeException.class, () -> StringSizeValidator.validateCategoryName(""));
    }

    @Test
    void validateCategoryName_whenCategoryNameIsTooLong_shouldThrowException() {
        assertThrows(StringSizeException.class, () -> StringSizeValidator.validateCategoryName("а".repeat(51)));
    }
}
