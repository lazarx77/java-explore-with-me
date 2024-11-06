package ru.practicum.exception;

public class StateActionException extends RuntimeException {
    public StateActionException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
