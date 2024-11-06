package ru.practicum.event.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Location {

    @NotNull(message = "Поле широта(lat) не может быть пустым")
    @Column(nullable = false)
    @Min(value = -90, message = "Поле широта(lat) не может быть меньше -90")
    @Max(value = 90, message = "Поле широта(lat) не может быть больше 90")
    private Float lat;
    @NotNull(message = "Поле долгота(lon) не может быть пустым")
    @Column(nullable = false)
    @Min(value = -180, message = "Поле долгота(lon) не может быть меньше -180")
    @Max(value = 180, message = "Поле долгота(lon) не может быть больше 180")
    private Float lon;
}
