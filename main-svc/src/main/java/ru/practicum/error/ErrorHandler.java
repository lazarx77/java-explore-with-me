package ru.practicum.error;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.exception.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        log.error("Возникла ошибка валидации MethodArgumentNotValidException: {}.", e.getMessage());

        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Поле: %s. Ошибка: %s. Переданное значение: %s",
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue() != null ? error.getRejectedValue() : "null"))
                .collect(Collectors.toList());

        String combinedErrorMessages = String.join(", ", errorMessages);

        return new ErrorResponse(
                getStackTrace(e),
                combinedErrorMessages,
                "Некорректные данные, предоставленные в запросе",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }


    @ExceptionHandler(EventDateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEventDateTimeException(final EventDateTimeException e) {
        log.error("Возникла ошибка BAD_REQUEST EventDateTimeException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                e.getMessage(),
                "Событие не удовлетворяет правилам редактирования.",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(LocationConstraintsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleLocationConstraintsException(final LocationConstraintsException e) {
        log.error("Возникла ошибка CONFLICT LocationConstraintsException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                e.getMessage(),
                "Событие не удовлетворяет правилам редактирования.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(StatePublishedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleStatePublishedException(final StatePublishedException e) {
        log.error("Возникла ошибка CONFLICT StatePublishedException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                e.getMessage(),
                "Событие не удовлетворяет правилам редактирования.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(StringSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEventStringSizeException(final StringSizeException e) {
        log.error("Возникла ошибка BAD_REQUEST StringSizeException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                e.getMessage(),
                "Длина текста не удовлетворяет правилам редактирования.",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(CategoryContainsEvents.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCategoryContainsEvents(final CategoryContainsEvents e) {
        log.error("Возникла ошибка CONFLICT CategoryContainsEvents: {}.", e.getMessage());

        return new

                ErrorResponse(
                getStackTrace(e),
                e.getMessage(),
                "Нарушено ограничение целостности.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Возникла ошибка валидации ConstraintViolationException: {}.", e.getMessage());

        List<String> errorMessages = e.getConstraintViolations().stream()
                .map(violation -> String.format("Поле: %s. Ошибка: %s. Переданное значение: %s",
                        violation.getPropertyPath(),
                        violation.getMessage(),
                        violation.getInvalidValue() != null ? violation.getInvalidValue() : "null"))
                .collect(Collectors.toList());

        String combinedErrorMessages = String.join(", ", errorMessages);

        return new ErrorResponse(
                getStackTrace(e),
                combinedErrorMessages,
                "Некорректные данные, предоставленные в запросе",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
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
        log.error("Ошибка преобразования параметра MethodArgumentTypeMismatchException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                "Поле: " + e.getName() + ". Ошибка: Неверный формат. Ожидался тип: " +
                        Objects.requireNonNull(e.getRequiredType()).getSimpleName() + ". А передано значение: " + e.getValue(),
                "Неверный формат передаваемых данных.",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
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
        log.error("Возникла ошибка валидации IllegalArgumentException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                "Ошибка валидации данных.",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(EmailDoubleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailDoubleException(final EmailDoubleException e) {
        log.error("Возникла ошибка CONFLICT EmailDoubleException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                e.getMessage(),
                "Нарушено ограничение целостности.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("Возникла ошибка валидации: {}.", e.getMessage());
        return new ErrorResponse(
                Collections.singletonList(e.getMessage()),
                "Ошибка валидации данных.",
                "Отсутствует обязательный параметр запроса " + e.getParameterName(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(RequestConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRequestConflictException(final RequestConflictException e) {
        log.error("Возникла ошибка CONFLICT RequestConflictException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                e.getMessage(),
                "Нарушено ограничение целостности.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(CategoryNameDoubleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCategoryNameDoubleException(final CategoryNameDoubleException e) {
        log.error("Возникла ошибка CONFLICT CategoryNameDoubleException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                e.getMessage(),
                "Нарушено ограничение целостности.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.error("Возникла ошибка NOT_FOUND NotFoundException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                "Возникла ошибка NOT_FOUND.",
                e.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
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

        log.error("Возникла ошибка INTERNAL_SERVER_ERROR Throwable: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTrace(e),
                "Возникла ошибка INTERNAL_SERVER_ERROR.",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    private List<String> getStackTrace(Throwable e) {
        return Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
    }
}
