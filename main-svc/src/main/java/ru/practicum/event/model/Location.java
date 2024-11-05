package ru.practicum.event.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
    private Float lat;
    @NotNull(message = "Поле долгота(lon) не может быть пустым")
    @Column(nullable = false)
    private Float lon;
}
