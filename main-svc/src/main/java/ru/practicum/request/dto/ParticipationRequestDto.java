package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

/**
 * DTO для представления запроса на участие в событии.
 */
@Getter
@Setter
@Builder
public class ParticipationRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private String status;
}
