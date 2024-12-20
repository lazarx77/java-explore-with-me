package ru.practicum.stats_server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.practicum.stats_server.service.DateTimeUtil.DATE_TIME_FORMAT;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime timestamp;

}
