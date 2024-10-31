package ru.practicum.stats_server.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Обработчик ошибок для контроллеров.
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Обрабатывает исключения валидации.
     *
     * @param e исключение валидации.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final MethodArgumentNotValidException e) {
        log.error("Возникла ошибка валидации: {}.", e.getMessage());

        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ErrorResponse(
                errors,
                "Ошибка валидации данных.",
                "Некорректные данные, предоставленные в запросе.",
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().toString()
        );
    }


    /**
     * Обрабатывает исключения несоответствия типов аргументов.
     *
     * @param e исключение несоответствия типов.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.error("Ошибка преобразования параметра: {}.", e.getMessage());

        return new ErrorResponse(
                Collections.singletonList("Неверный формат параметра: " + e.getName() + ". Ожидался тип: " +
                        e.getRequiredType().getSimpleName()),
                "Ошибка преобразования параметра.",
                "Пожалуйста, проверьте формат передаваемых данных.",
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().toString()
        );
    }

    /**
     * Обрабатывает исключения IllegalArgumentException.
     *
     * @param e исключение IllegalArgumentException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error("Возникла ошибка валидации: {}.", e.getMessage());

        return new ErrorResponse(
                Collections.singletonList(e.getMessage()),
                "Ошибка валидации данных.",
                "Некорректные данные, предоставленные в запросе.",
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().toString()
        );
    }

    /**
     * Обрабатывает все остальные исключения.
     *
     * @param e общее исключение.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error("Возникла ошибка INTERNAL_SERVER_ERROR: {}.", e.getMessage());

        return new ErrorResponse(
                Collections.singletonList(e.getMessage()),
                "Возникла ошибка INTERNAL_SERVER_ERROR.",
                "Для запрашиваемой операции условия не выполнены.",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().toString()
        );
    }
}
