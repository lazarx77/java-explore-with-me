package ru.practicum.compilation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.practicum.event.model.event.Event;

import java.util.List;

/**
 * Сущность компиляции событий, представляющая таблицу "compilations" в базе данных.
 * <p>
 * Этот класс используется для хранения информации о компиляциях, включая список событий,
 * статус закрепления и заголовок компиляции. Компиляции могут содержать несколько событий,
 * которые связываются через таблицу "compilation_events".
 */
@Entity
@Getter
@Setter
@Table(name = "compilations")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Compilation {

    @ManyToMany
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private Boolean pinned;
    @Column(nullable = false, length = 50, unique = true)
    @Size(min = 1, max = 50, message = "Поле title должно содержать от 1 до 50 символов.")
    @NotNull
    private String title;
}
