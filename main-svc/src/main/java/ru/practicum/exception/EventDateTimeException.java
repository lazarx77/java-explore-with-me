package ru.practicum.exception;

/**
 * Исключение, возникающее при некорректных значениях даты и времени события.
 */
public class EventDateTimeException extends RuntimeException {
    public EventDateTimeException(String message) {
        super(message);
    }
}