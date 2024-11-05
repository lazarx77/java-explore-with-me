package ru.practicum.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.DateTimeUtil.DATE_TIME_FORMAT;

/**
 * Ответ об ошибке, возвращаемый клиенту.
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<String> errors;
    private final String message;
    private final String reason;
    private final String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime timestamp;
}
