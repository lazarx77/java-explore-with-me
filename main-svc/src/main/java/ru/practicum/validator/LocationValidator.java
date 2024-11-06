package ru.practicum.validator;

import ru.practicum.event.model.Location;
import ru.practicum.exception.LocationConstraintsException;

public class LocationValidator {

    public static void validateLocation(Location location) {
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
