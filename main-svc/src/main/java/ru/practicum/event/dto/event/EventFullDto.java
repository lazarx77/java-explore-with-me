package ru.practicum.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.event.Location;
import ru.practicum.event.model.event.State;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

/**
 * Полная информация о событии.
 */
@Getter
@Setter
@Builder
public class EventFullDto {

    private String annotation;
    private Category category;
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;
    private Long id;
    private UserShortDto initiator;
    @Embedded
    private Location location;
    private Boolean paid;
    private int participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Long views;

}
