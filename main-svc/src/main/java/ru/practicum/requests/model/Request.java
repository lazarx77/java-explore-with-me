package ru.practicum.requests.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

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
    private LocalDateTime created;
    @NotNull(message = "Поле eventId не может быть пустым")
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @NotNull(message = "Поле requesterId не может быть пустым")
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
}
