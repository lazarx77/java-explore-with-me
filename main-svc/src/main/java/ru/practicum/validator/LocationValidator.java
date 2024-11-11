package ru.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.event.model.Location;
import ru.practicum.exception.LocationConstraintsException;


/**
 * Валидатор для проверки корректности географических координат местоположения.
 * Содержит методы для валидации широты и долготы.
 */
@Slf4j
public class LocationValidator {

    /**
     * Валидатор для проверки корректности географических координат местоположения.
     * Содержит методы для валидации широты и долготы.
     */
    public static void validateLocation(Location location) {
        log.info("Валидация географических координат местоположения: {}", location);
        float lat = location.getLat();
        if (lat < -90 || lat > 90) {
            throw new LocationConstraintsException("Неверное значение широты(lat): " + lat);
        }
        float lon = location.getLon();
        if (lon < -180 || lon > 180) {
            throw new LocationConstraintsException("Неверное значение долготы(lon): " + lon);
        }
    }
}
