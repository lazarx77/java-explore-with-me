package ru.practicum.exception;

public class EmailDoubleException extends RuntimeException {

    public EmailDoubleException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
