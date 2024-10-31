package ru.practicum.stats_server.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    private final String timestamp;
}
