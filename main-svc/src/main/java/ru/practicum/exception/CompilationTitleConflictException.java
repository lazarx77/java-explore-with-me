package ru.practicum.exception;

/**
 * Исключение, возникающее при конфликте заголовков компиляции.
 */
public class CompilationTitleConflictException extends RuntimeException {
    public CompilationTitleConflictException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
