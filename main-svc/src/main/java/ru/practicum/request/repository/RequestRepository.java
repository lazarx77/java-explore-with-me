package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.request.model.Request;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с запросами на участие в событиях.
 * Предоставляет методы для выполнения операций CRUD и дополнительных запросов.
 */
public interface RequestRepository extends JpaRepository<Request, Long> {

    /**
     * Находит запрос по идентификатору запрашивающего и идентификатору события.
     *
     * @param userId  Идентификатор запрашивающего.
     * @param eventId Идентификатор события.
     * @return Optional, содержащий найденный запрос, если он существует.
     */
    Optional<Request> findByRequesterIdAndEventId(Long userId, Long eventId);

    /**
     * Находит запрос по его идентификатору и идентификатору запрашивающего.
     *
     * @param requestId   Идентификатор запроса.
     * @param requesterId Идентификатор запрашивающего.
     * @return Optional, содержащий найденный запрос, если он существует.
     */
    Optional<Request> findByIdAndRequesterId(Long requestId, Long requesterId);

    /**
     * Находит все запросы, сделанные пользователем с указанным идентификатором.
     *
     * @param userId Идентификатор запрашивающего.
     * @return Список запросов, сделанных пользователем.
     */
    List<Request> findAllByRequesterId(Long userId);

    /**
     * Находит все запросы на участие в событии по идентификатору инициатора и идентификатору события.
     *
     * @param initiatorId Идентификатор инициатора события.
     * @param eventId     Идентификатор события.
     * @return Список запросов, связанных с указанным событием и инициатором.
     */
    @Query(value = "SELECT r.* FROM requests r " +
            "JOIN events e ON r.event_id = e.id " +
            "WHERE e.initiator_id = :initiatorId AND r.event_id = :eventId",
            nativeQuery = true)
    List<Request> findAllByInitiatorIdAndEventId(@Param("initiatorId") Long initiatorId,
                                                 @Param("eventId") Long eventId);

    /**
     * Находит все запросы по списку идентификаторов и идентификатору события.
     *
     * @param requestIds Список идентификаторов запросов.
     * @param eventId    Идентификатор события.
     * @return Список запросов, соответствующих указанным идентификаторам и событию.
     */
    @Query("SELECT r FROM Request r WHERE r.id IN :requestIds AND r.event.id = :eventId")
    List<Request> findAllByIdsAndEventId(@Param("requestIds") List<Long> requestIds, @Param("eventId") Long eventId);
}
