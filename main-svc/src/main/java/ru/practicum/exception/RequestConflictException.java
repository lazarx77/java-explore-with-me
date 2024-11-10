package ru.practicum.exception;

/**
 * Исключение, возникающее при конфликте запросов на участие в соботиях.
 */
public class RequestConflictException extends RuntimeException {
    public RequestConflictException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
