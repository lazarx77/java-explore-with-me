package ru.practicum.stats_server.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.stats_server.service.DateTimeUtil.DATE_TIME_FORMAT;

/**
 * Ответ об ошибке, возвращаемый клиенту.
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final List<String> errors;
    private final String message;
    private final String reason;
    private final String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private final LocalDateTime timestamp;
}
