package ru.practicum.exception;

/**
 * Исключение, возникающее при недопустимых действиях со состоянием события.
 */
public class StateActionException extends RuntimeException {
    public StateActionException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
