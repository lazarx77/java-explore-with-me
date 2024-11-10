package ru.practicum.exception;

/**
 * Исключение, выбрасываемое при попытке создать категорию с дублирующимся именем.
 */
public class CategoryNameDoubleException extends RuntimeException {

    public CategoryNameDoubleException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
