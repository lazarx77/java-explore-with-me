package ru.practicum.validator;

import org.junit.jupiter.api.Test;
import ru.practicum.event.model.Location;
import ru.practicum.exception.LocationConstraintsException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности валидатора LocationValidator.
 */
class LocationValidatorTest {

    @Test
    void validateLocation_whenLatitudeAndLongitudeAreValid_shouldNotThrowException() {
        Location validLocation = new Location(45.0f, 90.0f);

        assertDoesNotThrow(() -> LocationValidator.validateLocation(validLocation));
    }

    @Test
    void validateLocation_whenLatitudeIsTooLow_shouldThrowException() {
        Location invalidLocation = new Location(-91.0f, 90.0f);

        assertThrows(LocationConstraintsException.class, () ->
                LocationValidator.validateLocation(invalidLocation)
        );
    }

    @Test
    void validateLocation_whenLatitudeIsTooHigh_shouldThrowException() {
        Location invalidLocation = new Location(91.0f, 90.0f);

        assertThrows(LocationConstraintsException.class, () ->
                LocationValidator.validateLocation(invalidLocation)
        );
    }

    @Test
    void validateLocation_whenLongitudeIsTooLow_shouldThrowException() {
        Location invalidLocation = new Location(45.0f, -181.0f);

        assertThrows(LocationConstraintsException.class, () ->
                LocationValidator.validateLocation(invalidLocation)
        );
    }

    @Test
    void validateLocation_whenLongitudeIsTooHigh_shouldThrowException() {
        Location invalidLocation = new Location(45.0f, 181.0f);

        assertThrows(LocationConstraintsException.class, () ->
                LocationValidator.validateLocation(invalidLocation)
        );
    }
}
