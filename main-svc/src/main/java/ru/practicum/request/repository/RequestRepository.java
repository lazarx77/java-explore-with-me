package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request> findByRequesterIdAndEventId(Long userId, Long eventId);

    Optional<Request> findByIdAndRequesterId(Long requestId, Long requesterId);

    List<Request> findAllByRequesterId(Long userId);

    @Query(value = "SELECT r.* FROM requests r " +
            "JOIN events e ON r.event_id = e.id " +
            "WHERE e.initiator_id = :initiatorId AND r.event_id = :eventId",
            nativeQuery = true)
    List<Request> findAllByInitiatorIdAndEventId(@Param("initiatorId") Long initiatorId,
                                                 @Param("eventId") Long eventId);

    @Query("SELECT r FROM Request r WHERE r.id IN :requestIds AND r.event.id = :eventId")
    List<Request> findAllByIdsAndEventId(@Param("requestIds") List<Long> requestIds, @Param("eventId") Long eventId);
}
