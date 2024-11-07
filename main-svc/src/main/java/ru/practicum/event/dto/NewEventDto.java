package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.model.Location;

import java.time.LocalDateTime;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

@Getter
@Setter
public class NewEventDto {

    @NotBlank(message = "Поле annotation не может быть пустым")
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull(message = "Поле category не может быть пустым")
    private Long category;
    @NotBlank(message = "Поле description не может быть пустым")
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull(message = "Поле eventDate не может быть пустым")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;
    @Embedded
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank(message = "Поле title не может быть пустым")
    @Size(min = 3, max = 120)
    private String title;
}
