package ru.practicum.validator;

import org.junit.jupiter.api.Test;
import ru.practicum.exception.EventDateTimeException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности валидатора даты и времени.
 */
class DateTimeValidatorTest {

    @Test
    void validateDateTime_whenDateTimeIsValid_shouldNotThrowException() {
        LocalDateTime validDateTime = LocalDateTime.now().plusHours(3);

        assertDoesNotThrow(() -> DateTimeValidator.validateDateTime(validDateTime));
    }

    @Test
    void validateDateTime_whenDateTimeIsTooEarly_shouldThrowException() {
        LocalDateTime invalidDateTime = LocalDateTime.now().plusMinutes(90);

        assertThrows(EventDateTimeException.class, () ->
                DateTimeValidator.validateDateTime(invalidDateTime)
        );
    }

    @Test
    void validateDateTimeOnUpdate_whenDateTimeIsValid_shouldNotThrowException() {
        LocalDateTime validDateTime = LocalDateTime.now().plusHours(2);

        assertDoesNotThrow(() -> DateTimeValidator.validateDateTimeOnUpdate(validDateTime));
    }

    @Test
    void validateDateTimeOnUpdate_whenDateTimeIsTooEarly_shouldThrowException() {
        LocalDateTime invalidDateTime = LocalDateTime.now().plusMinutes(30);

        assertThrows(EventDateTimeException.class, () ->
                DateTimeValidator.validateDateTimeOnUpdate(invalidDateTime)
        );
    }
}
