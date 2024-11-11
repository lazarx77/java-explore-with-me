package ru.practicum.validator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности валидатора CompilationEventsUniqueValidator.
 */
class CompilationEventsUniqueValidatorTest {

    @Test
    void validateEventsUnique_whenIdsAreUnique_shouldNotThrowException() {
        List<Long> uniqueIds = Arrays.asList(1L, 2L, 3L, 4L);

        assertDoesNotThrow(() -> CompilationEventsUniqueValidator.validateEventsUnique(uniqueIds));
    }

    @Test
    void validateEventsUnique_whenIdsAreNotUnique_shouldThrowException() {
        List<Long> duplicateIds = Arrays.asList(1L, 2L, 2L, 4L);

        assertThrows(IllegalArgumentException.class, () ->
                CompilationEventsUniqueValidator.validateEventsUnique(duplicateIds)
        );
    }

    @Test
    void validateEventsUnique_whenIdsListIsEmpty_shouldNotThrowException() {
        List<Long> emptyIds = Collections.emptyList();

        assertDoesNotThrow(() -> CompilationEventsUniqueValidator.validateEventsUnique(emptyIds));
    }
}
