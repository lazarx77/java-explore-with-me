package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.model.Location;

import java.time.LocalDateTime;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

/**
 * Запрос на обновление события от администратора.
 * Содержит информацию о полях, которые могут быть изменены при редактировании события.
 */
@Getter
@Setter
public class UpdateEventAdminRequest {

    String annotation;
    Long category;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    LocalDateTime eventDate;
    @Embedded
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String stateAction;
    String title;
}

