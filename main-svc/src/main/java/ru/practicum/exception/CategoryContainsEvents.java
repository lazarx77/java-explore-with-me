package ru.practicum.exception;

public class CategoryContainsEvents extends RuntimeException {
    public CategoryContainsEvents(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
