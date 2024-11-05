package ru.practicum.exception;

public class CategoryNameDoubleException extends RuntimeException {

    public CategoryNameDoubleException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
