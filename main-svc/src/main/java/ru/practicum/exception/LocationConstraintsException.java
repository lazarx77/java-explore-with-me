package ru.practicum.exception;

/**
 * Исключение, возникающее при нарушении ограничений местоположения.
 */
public class LocationConstraintsException extends RuntimeException {
    public LocationConstraintsException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
