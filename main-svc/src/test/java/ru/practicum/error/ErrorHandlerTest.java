package ru.practicum.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.exception.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тестовый класс для {@link ErrorHandler}.
 */
@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {

    @InjectMocks
    private ErrorHandler errorHandler;

    @Test
    void handleEventDateTimeException_whenValidException_thenReturnErrorResponse() {
        EventDateTimeException exception = new EventDateTimeException("Ошибка времени события");

        ErrorResponse response = errorHandler.handleEventDateTimeException(exception);

        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Ошибка времени события", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleLocationConstraintsException_whenValidException_thenReturnErrorResponse() {
        LocationConstraintsException exception = new LocationConstraintsException("Нарушение ограничений " +
                "местоположения");

        ErrorResponse response = errorHandler.handleLocationConstraintsException(exception);

        assertEquals(HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Нарушение ограничений местоположения", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleStatePublishedException_whenValidException_thenReturnErrorResponse() {
        StatePublishedException exception = new StatePublishedException("Состояние уже опубликовано");

        ErrorResponse response = errorHandler.handleStatePublishedException(exception);

        assertEquals(HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Состояние уже опубликовано", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleStringSizeException_whenValidException_thenReturnErrorResponse() {
        StringSizeException exception = new StringSizeException("Длина строки не соответствует требованиям");

        ErrorResponse response = errorHandler.handleEventStringSizeException(exception);

        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Длина строки не соответствует требованиям", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleCategoryContainsEvents_whenValidException_thenReturnErrorResponse() {
        CategoryContainsEvents exception = new CategoryContainsEvents("Категория содержит события");

        ErrorResponse response = errorHandler.handleCategoryContainsEvents(exception);

        assertEquals(HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Категория содержит события", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleEmailDoubleException_whenValidException_thenReturnErrorResponse() {
        EmailDoubleException exception = new EmailDoubleException("Электронная почта уже существует");

        ErrorResponse response = errorHandler.handleEmailDoubleException(exception);

        assertEquals(HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Электронная почта уже существует", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleMissingServletRequestParameterException_whenValidException_thenReturnErrorResponse() {
        MissingServletRequestParameterException exception =
                new MissingServletRequestParameterException("обязательный параметр",
                        "Отсутствует обязательный параметр");

        ErrorResponse response = errorHandler.handleMissingServletRequestParameterException(exception);

        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getStatus());
        assertEquals("Ошибка валидации данных.", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleMethodArgumentTypeMismatchException_whenValidException_thenReturnErrorResponse() {
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException("param", String.class,
                null, null, null);

        ErrorResponse response = errorHandler.handleTypeMismatchException(exception);

        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Поле: null. Ошибка: Неверный формат. Ожидался тип: String. А передано значение: param",
                response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleIllegalArgumentException_whenValidException_thenReturnErrorResponse() {
        IllegalArgumentException exception = new IllegalArgumentException("Некорректный аргумент");

        ErrorResponse response = errorHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Ошибка валидации данных.", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleRequestConflictException_whenValidException_thenReturnErrorResponse() {
        RequestConflictException exception = new RequestConflictException("Конфликт запроса");

        ErrorResponse response = errorHandler.handleRequestConflictException(exception);

        assertEquals(HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Конфликт запроса", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleCategoryNameDoubleException_whenValidException_thenReturnErrorResponse() {
        CategoryNameDoubleException exception = new CategoryNameDoubleException("Имя категории уже существует");

        ErrorResponse response = errorHandler.handleCategoryNameDoubleException(exception);

        assertEquals(HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Имя категории уже существует", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleNotFoundException_whenValidException_thenReturnErrorResponse() {
        NotFoundException exception = new NotFoundException("Не найдено");

        ErrorResponse response = errorHandler.handleNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Возникла ошибка NOT_FOUND.", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void handleThrowable_whenGeneralException_thenReturnErrorResponse() {
        Throwable exception = new Throwable("Общая ошибка");

        ErrorResponse response = errorHandler.handleThrowable(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().toUpperCase(), response.getStatus());
        assertEquals("Возникла ошибка INTERNAL_SERVER_ERROR.", response.getMessage());
        assertNotNull(response.getTimestamp());
    }
}
