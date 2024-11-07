package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    @Query(value = "SELECT * FROM events WHERE initiator_id = :userId ORDER BY created_on DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Event> findAllByInitiatorId(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);

    Optional<Event> findByInitiatorIdAndId(Long userId, Long eventId);

    Optional<Event> findByIdAndState(Long eventId, State state);
}
