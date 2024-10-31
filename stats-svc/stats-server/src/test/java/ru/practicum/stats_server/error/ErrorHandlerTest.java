package ru.practicum.stats_server.error;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для обработчика ошибок.
 */
class ErrorHandlerTest {

    private final ErrorHandler errorHandler = new ErrorHandler();

    @Test
    void handleValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "error message");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ErrorResponse response = errorHandler.handleValidationException(exception);

        assertEquals("400 BAD_REQUEST", response.getStatus());
        assertEquals("Ошибка валидации данных.", response.getMessage());
        assertEquals("Некорректные данные, предоставленные в запросе.", response.getReason());
        assertEquals(1, response.getErrors().size());
        assertEquals("fieldName: error message", response.getErrors().getFirst());
    }

    @Test
    void handleTypeMismatchException() {
        // Подготовка данных для теста
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("paramName");
        when(exception.getRequiredType()).thenReturn((Class) String.class); // Приведение к Class

        // Вызов метода обработчика
        ErrorResponse response = errorHandler.handleTypeMismatchException(exception);

        // Проверка результатов
        assertEquals("400 BAD_REQUEST", response.getStatus());
        assertEquals("Ошибка преобразования параметра.", response.getMessage());
        assertEquals("Пожалуйста, проверьте формат передаваемых данных.", response.getReason());
        assertEquals(1, response.getErrors().size());
        assertEquals("Неверный формат параметра: paramName. Ожидался тип: String", response.getErrors().getFirst());
    }


    @Test
    void handleIllegalArgumentException() {
        // Подготовка данных для теста
        IllegalArgumentException exception = new IllegalArgumentException("Некорректные данные");

        // Вызов метода обработчика
        ErrorResponse response = errorHandler.handleIllegalArgumentException(exception);

        // Проверка результатов
        assertEquals("400 BAD_REQUEST", response.getStatus());
        assertEquals("Ошибка валидации данных.", response.getMessage());
        assertEquals("Некорректные данные, предоставленные в запросе.", response.getReason());
        assertEquals(1, response.getErrors().size());
        assertEquals("Некорректные данные", response.getErrors().getFirst());
    }

    @Test
    void handleThrowable() {
        // Подготовка данных для теста
        Throwable exception = new Throwable("Внутренняя ошибка сервера");

        // Вызов метода обработчика
        ErrorResponse response = errorHandler.handleThrowable(exception);

        // Проверка результатов
        assertEquals("500 INTERNAL_SERVER_ERROR", response.getStatus());
        assertEquals("Возникла ошибка INTERNAL_SERVER_ERROR.", response.getMessage());
        assertEquals("Для запрашиваемой операции условия не выполнены.", response.getReason());
        assertEquals(1, response.getErrors().size());
        assertEquals("Внутренняя ошибка сервера", response.getErrors().getFirst());
    }
}
