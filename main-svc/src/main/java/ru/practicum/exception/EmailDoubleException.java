package ru.practicum.exception;

/**
 * Исключение, возникающее при попытке зарегистрировать адрес электронной почты,
 * который уже существует в системе.
 */
public class EmailDoubleException extends RuntimeException {

    public EmailDoubleException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
