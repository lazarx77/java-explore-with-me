package ru.practicum.exception;

/**
 * Исключение, возникающее при попытке изменить состояние уже опубликованного объекта.
 */
public class StatePublishedException extends RuntimeException {

    public StatePublishedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
