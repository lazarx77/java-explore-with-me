package ru.practicum.exception;

/**
 * Исключение, возникающее, когда запрашиваемый ресурс не найден.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
