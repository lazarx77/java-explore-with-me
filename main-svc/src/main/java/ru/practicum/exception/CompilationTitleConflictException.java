package ru.practicum.exception;

public class CompilationTitleConflictException extends RuntimeException {
    public CompilationTitleConflictException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
