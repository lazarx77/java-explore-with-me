package ru.practicum.event.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;


/**
 * Класс, представляющий событие в системе.
 * Содержит информацию о событии, включая аннотацию, описание, дату события, инициатора и другие параметры.
 */
@Entity
@Getter
@Setter
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Column(nullable = false)
    @NotBlank(message = "Поле annotation не может быть пустым")
    @Size(min = 20, max = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Поле category не может быть null")
    private Category category;

    @Column(name = "confirmed_requests")
    private Long confirmedRequests;

    @Column(name = "created_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    @NotBlank(message = "Поле description не может быть пустым")
    @Size(min = 20, max = 7000)
    private String description;

    @Column(name = "event_date", nullable = false)
    @NotNull(message = "Поле eventDate не может быть null")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    @NotNull(message = "Поле initiator не может быть null")
    private User initiator;

    @Embedded
    private Location location;

    @Column(nullable = false)
    @NotNull(message = "Поле paid не может быть null")
    private Boolean paid;

    @Column(name = "participant_limit", nullable = false)
    @PositiveOrZero(message = "Поле participantLimit должно быть 0 или положительным числом")
    private int participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(nullable = false)
    @NotBlank(message = "Поле title не может быть пустым")
    @Size(min = 3, max = 120)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "compilation_id")
    )
    private List<Compilation> compilations;
}
