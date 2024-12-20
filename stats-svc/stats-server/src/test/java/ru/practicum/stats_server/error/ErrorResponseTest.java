package ru.practicum.stats_server.error;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {

    @Test
    void testErrorResponseCreation() {
        List<String> errorList = Arrays.asList("Error 1", "Error 2");
        String message = "An error occurred";
        String reason = "Invalid input";
        String status = "400 BAD REQUEST";
        LocalDateTime timestamp = LocalDateTime.of(2023, 10, 1, 12, 0, 0, 0);

        ErrorResponse errorResponse = new ErrorResponse(errorList, message, reason, status, timestamp);

        assertEquals(errorList, errorResponse.getErrors());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(reason, errorResponse.getReason());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(timestamp, errorResponse.getTimestamp());
    }

    @Test
    void testErrorResponseGetters() {
        List<String> errorList = Arrays.asList("Error 1");
        ErrorResponse errorResponse = new ErrorResponse(errorList, "Some message", "Some reason",
                "404 NOT FOUND", LocalDateTime.of(2023, 10, 1, 12, 0, 0, 0));

        assertEquals(errorList, errorResponse.getErrors());
        assertEquals("Some message", errorResponse.getMessage());
        assertEquals("Some reason", errorResponse.getReason());
        assertEquals("404 NOT FOUND", errorResponse.getStatus());
        assertEquals("2023-10-01T12:00", errorResponse.getTimestamp().toString());
    }
}
