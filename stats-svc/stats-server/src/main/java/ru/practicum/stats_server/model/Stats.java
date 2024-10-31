package ru.practicum.stats_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Модель для хранения статистики запросов.
 */
@Entity
@Getter
@Setter
@Table(name = "stats")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String app;

    @NotNull
    @Column(nullable = false)
    private String uri;

    @NotNull
    @Column(nullable = false, length = 45)
    private String ip;

    @NotNull
    @Column(name = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;

}
