package ru.practicum.validator;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности валидатора параметров.
 */
class ParamsValidatorTest {

    @Test
    void validateGetEventsPublicParams_whenSortIsValid_shouldNotThrowException() {
        assertDoesNotThrow(() ->
                ParamsValidator.validateGetEventsPublicParams(null, null, null, "EVENT_DATE")
        );

        assertDoesNotThrow(() ->
                ParamsValidator.validateGetEventsPublicParams(null, null, null, "VIEWS")
        );
    }

    @Test
    void validateGetEventsPublicParams_whenSortIsInvalid_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                ParamsValidator.validateGetEventsPublicParams(null, null, null, "INVALID_SORT")
        );
    }

    @Test
    void validateGetEventsPublicParams_whenCategoriesContainNegativeValue_shouldThrowException() {
        Long[] categories = {1L, -2L, 3L};
        assertThrows(IllegalArgumentException.class, () ->
                ParamsValidator.validateGetEventsPublicParams(categories, null, null, null)
        );
    }

    @Test
    void validateGetEventsPublicParams_whenCategoriesAreValid_shouldNotThrowException() {
        Long[] categories = {1L, 2L, 3L};
        assertDoesNotThrow(() ->
                ParamsValidator.validateGetEventsPublicParams(categories, null, null, null)
        );
    }

    @Test
    void validateGetEventsPublicParams_whenRangeStartIsAfterRangeEnd_shouldThrowException() {
        LocalDateTime rangeStart = LocalDateTime.now().plusDays(1);
        LocalDateTime rangeEnd = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () ->
                ParamsValidator.validateGetEventsPublicParams(null, rangeStart, rangeEnd, null)
        );
    }

    @Test
    void validateGetEventsPublicParams_whenRangeIsValid_shouldNotThrowException() {
        LocalDateTime rangeStart = LocalDateTime.now();
        LocalDateTime rangeEnd = LocalDateTime.now().plusDays(1);
        assertDoesNotThrow(() ->
                ParamsValidator.validateGetEventsPublicParams(null, rangeStart, rangeEnd, null)
        );
    }
}
