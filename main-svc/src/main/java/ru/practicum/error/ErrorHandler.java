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
 * Перехватывает и обрабатывает различные исключения, возникающие в приложении,
 * и возвращает соответствующие ответы с информацией об ошибках.
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
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Возникла ошибка валидации MethodArgumentNotValidException: {}.", e.getMessage());

        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Поле: %s. Ошибка: %s. Переданное значение: %s",
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue() != null ? error.getRejectedValue() : "null"))
                .collect(Collectors.toList());

        String combinedErrorMessages = String.join(", ", errorMessages);

        return new ErrorResponse(
                getStackTraces(e),
                combinedErrorMessages,
                "Некорректные данные, предоставленные в запросе",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с датой и временем события.
     *
     * @param e исключение EventDateTimeException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(EventDateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEventDateTimeException(final EventDateTimeException e) {
        log.error("Возникла ошибка BAD_REQUEST EventDateTimeException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                e.getMessage(),
                "Событие не удовлетворяет правилам редактирования.",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с ограничениями местоположения.
     *
     * @param e исключение LocationConstraintsException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(LocationConstraintsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleLocationConstraintsException(final LocationConstraintsException e) {
        log.error("Возникла ошибка CONFLICT LocationConstraintsException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                e.getMessage(),
                "Событие не удовлетворяет правилам редактирования.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с состоянием события.
     *
     * @param e исключение StatePublishedException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(StatePublishedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleStatePublishedException(final StatePublishedException e) {
        log.error("Возникла ошибка CONFLICT StatePublishedException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                e.getMessage(),
                "Событие не удовлетворяет правилам редактирования.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с размером строки.
     *
     * @param e исключение StringSizeException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(StringSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEventStringSizeException(final StringSizeException e) {
        log.error("Возникла ошибка BAD_REQUEST StringSizeException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                e.getMessage(),
                "Длина текста не удовлетворяет правилам редактирования.",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с категориями, содержащими события.
     *
     * @param e исключение CategoryContainsEvents.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(CategoryContainsEvents.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCategoryContainsEvents(final CategoryContainsEvents e) {
        log.error("Возникла ошибка CONFLICT CategoryContainsEvents: {}.", e.getMessage());

        return new

                ErrorResponse(
                getStackTraces(e),
                e.getMessage(),
                "Нарушено ограничение целостности.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с нарушением ограничений валидации.
     *
     * @param e исключение ConstraintViolationException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
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
                getStackTraces(e),
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
                getStackTraces(e),
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
                getStackTraces(e),
                "Ошибка валидации данных.",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с дублированием email.
     *
     * @param e исключение EmailDoubleException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(EmailDoubleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailDoubleException(final EmailDoubleException e) {
        log.error("Возникла ошибка CONFLICT EmailDoubleException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                e.getMessage(),
                "Нарушено ограничение целостности.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с отсутствием обязательных параметров запроса.
     *
     * @param e исключение MissingServletRequestParameterException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
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

    /**
     * Обрабатывает исключения конфликта запросов.
     *
     * @param e исключение RequestConflictException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(RequestConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRequestConflictException(final RequestConflictException e) {
        log.error("Возникла ошибка CONFLICT RequestConflictException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                e.getMessage(),
                "Нарушено ограничение целостности.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает исключения, связанные с отсутствующими ресурсами.
     *
     * @param e исключение NotFoundException.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(CategoryNameDoubleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCategoryNameDoubleException(final CategoryNameDoubleException e) {
        log.error("Возникла ошибка CONFLICT CategoryNameDoubleException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                e.getMessage(),
                "Нарушено ограничение целостности.",
                HttpStatus.CONFLICT.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    /**
     * Обрабатывает все остальные исключения, не попадающие под предыдущие обработчики.
     *
     * @param e общее исключение.
     * @return объект ErrorResponse с информацией об ошибке.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.error("Возникла ошибка NOT_FOUND NotFoundException: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                "Возникла ошибка NOT_FOUND.",
                e.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );

    }

    /**
     * Получает стек вызовов для данного исключения.
     *
     * @param e исключение, для которого нужно получить стек вызовов.
     * @return список строк, представляющих стек вызовов.
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {

        log.error("Возникла ошибка INTERNAL_SERVER_ERROR Throwable: {}.", e.getMessage());

        return new ErrorResponse(
                getStackTraces(e),
                "Возникла ошибка INTERNAL_SERVER_ERROR.",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().toUpperCase(),
                LocalDateTime.now()
        );
    }

    private List<String> getStackTraces(Throwable e) {
        if (e == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
    }
}
