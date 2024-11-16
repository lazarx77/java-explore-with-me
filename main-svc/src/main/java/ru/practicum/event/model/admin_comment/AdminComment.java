package ru.practicum.event.model.admin_comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.event.model.event.Event;

import java.time.LocalDateTime;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;


/**
 * Сущность комментария администратора.
 * <p>
 * Этот класс представляет собой комментарий, оставленный администратором
 * к событию. Он содержит текст комментария, дату создания и связь с событием.
 */
@Entity
@Table(name = "admin_comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false, length = 1200)
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    @NotNull
    private LocalDateTime created;

}
