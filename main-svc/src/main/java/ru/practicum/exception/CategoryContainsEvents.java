package ru.practicum.exception;

/**
 * Исключение, выбрасываемое, когда категория содержит события.
 */
public class CategoryContainsEvents extends RuntimeException {
    public CategoryContainsEvents(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
