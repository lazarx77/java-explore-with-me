package ru.practicum.request.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

/**
 * Модель запроса на участие в событии.
 */
@Entity
@Getter
@Setter
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime created;
    @NotNull(message = "Поле eventId не может быть пустым")
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
}
