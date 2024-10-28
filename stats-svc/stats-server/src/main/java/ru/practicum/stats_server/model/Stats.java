package ru.practicum.stats_server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "stats")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Идентификатор записи

    @Column(nullable = false)
    private String app; // Название сервиса, из которого был осуществлен запрос

    @Column(nullable = false)
    private String uri; // URI для которого был осуществлен запрос

    @Column(nullable = false, length = 45)
    private String ip; // IP-адрес пользователя

    @Column(name = "timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp; // Дата и время запроса

}
