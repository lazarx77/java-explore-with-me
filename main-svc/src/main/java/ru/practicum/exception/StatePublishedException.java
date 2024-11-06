package ru.practicum.exception;

public class StatePublishedException extends RuntimeException {

    public StatePublishedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
