package ru.practicum.request.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.request.model.Status;

import java.util.List;

/**
 * DTO для обновления статуса запросов на участие в событии.
 */
@Getter
@Setter
public class EventRequestStatusUpdateRequestDto {

    @NotNull
    private List<Long> requestIds;
    @NotNull
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private Status status;
}
