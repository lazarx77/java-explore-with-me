package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request>  findByRequesterIdAndEventId(Long userId, Long eventId);

    List<Request> findAllByRequesterId(Long userId);

    List<Request> findAllByEventIdAndRequesterId(Long eventId, Long userId);

    @Query("SELECT r FROM Request r WHERE r.id IN :requestIds AND r.event.id = :eventId")
    List<Request> findAllByIdsAndEventId(@Param("requestIds") List<Long> requestIds, @Param("eventId") Long eventId);
}
