package ru.practicum.exception;

public class StringSizeException extends RuntimeException {

    public StringSizeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
