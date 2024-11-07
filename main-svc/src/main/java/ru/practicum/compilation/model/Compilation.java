package ru.practicum.compilation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.event.model.Event;

import java.util.List;

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
    @Column(nullable = false, length = 50)
    @Min(1)
    @Max(50)
    @NotNull
    private String title;
}
