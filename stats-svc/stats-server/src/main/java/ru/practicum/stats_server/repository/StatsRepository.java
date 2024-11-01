package ru.practicum.stats_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.stats_dto.StatsResponseDto;
import ru.practicum.stats_server.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью Stats.
 */
public interface StatsRepository extends JpaRepository<Stats, Long> {

    /**
     * Получает сводную статистику запросов за указанный период.
     *
     * @param start начальная дата и время.
     * @param end   конечная дата и время.
     * @param uris  список URI (может быть null).
     * @return список объектов StatsResponseDto с данными статистики.
     */
    @Query("SELECT new ru.practicum.stats_dto.StatsResponseDto(s.app, s.uri, COUNT(s)) " +
            "FROM Stats s " +
            "WHERE s.timestamp BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR s.uri IN :uris) " +
            "GROUP BY s.app, s.uri")
    List<StatsResponseDto> findStatsSummaryBetween(@Param("start") LocalDateTime start,
                                                   @Param("end") LocalDateTime end,
                                                   @Param("uris") List<String> uris);

    /**
     * Получает сводную статистику уникальных IP-адресов за указанный период.
     *
     * @param start начальная дата и время.
     * @param end   конечная дата и время.
     * @param uris  список URI (может быть null).
     * @return список объектов StatsResponseDto с данными статистики.
     */
    @Query("SELECT new ru.practicum.stats_dto.StatsResponseDto(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM Stats s " +
            "WHERE s.timestamp BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR s.uri IN :uris) " +
            "GROUP BY s.app, s.uri")
    List<StatsResponseDto> findUniqueIpStatsSummaryBetween(@Param("start") LocalDateTime start,
                                                           @Param("end") LocalDateTime end,
                                                           @Param("uris") List<String> uris);
}
