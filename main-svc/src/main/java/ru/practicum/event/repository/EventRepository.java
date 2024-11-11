package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с событиями.
 * Наследует функциональность JpaRepository и предоставляет дополнительные методы для работы с событиями.
 */
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    /**
     * Находит все события по идентификатору инициатора с пагинацией.
     *
     * @param userId идентификатор инициатора.
     * @param offset смещение для пагинации.
     * @param limit  лимит количества возвращаемых событий.
     * @return список событий.
     */
    @Query(value = "SELECT * FROM events WHERE initiator_id = :userId ORDER BY created_on DESC LIMIT :limit " +
            "OFFSET :offset", nativeQuery = true)
    List<Event> findAllByInitiatorId(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * Находит событие по идентификатору инициатора и идентификатору события.
     *
     * @param userId  идентификатор инициатора.
     * @param eventId идентификатор события.
     * @return Optional<Event> - найденное событие или пустой Optional.
     */
    Optional<Event> findByInitiatorIdAndId(Long userId, Long eventId);

    /**
     * Находит событие по его идентификатору и состоянию.
     *
     * @param eventId идентификатор события.
     * @param state   состояние события.
     * @return Optional<Event> - найденное событие или пустой Optional.
     */
    Optional<Event> findByIdAndState(Long eventId, State state);

    /**
     * Находит все события по списку идентификаторов.
     *
     * @param ids список идентификаторов событий.
     * @return список событий.
     */
    @Query("SELECT e FROM Event e WHERE (:ids IS NULL OR e.id IN :ids)")
    List<Event> findAllByIds(@Param("ids") List<Long> ids);

    /**
     * Находит все события по идентификатору категории.
     *
     * @param catId идентификатор категории.
     * @return список событий.
     */
    List<Event> findAllByCategoryId(Long catId);
}
