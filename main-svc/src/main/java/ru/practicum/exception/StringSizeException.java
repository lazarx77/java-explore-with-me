package ru.practicum.exception;

/**
 * Исключение, возникающее при нарушении ограничений на размер строки.
 */
public class StringSizeException extends RuntimeException {

    public StringSizeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
