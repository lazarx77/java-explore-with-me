package ru.practicum.exception;

public class LocationConstraintsException extends RuntimeException {
    public LocationConstraintsException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
