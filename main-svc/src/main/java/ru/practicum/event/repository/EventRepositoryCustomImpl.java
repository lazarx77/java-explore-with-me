package ru.practicum.event.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> getEvents(List<Long> userList,
                                 List<String> stateList,
                                 List<Long> categoryList,
                                 LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd,
                                 int offset,
                                 int limit) {
        StringBuilder queryString = new StringBuilder("SELECT e FROM Event e WHERE 1=1");

        if (userList != null && !userList.isEmpty()) {
            queryString.append(" AND e.initiator.id IN :users");
        }
        if (stateList != null && !stateList.isEmpty()) {
            queryString.append(" AND e.state IN :states");
        }
        if (categoryList != null && !categoryList.isEmpty()) {
            queryString.append(" AND e.category.id IN :categories");
        }
        if (rangeStart != null) {
            queryString.append(" AND e.eventDate >= :rangeStart");
        }
        if (rangeEnd != null) {
            queryString.append(" AND e.eventDate <= :rangeEnd");
        }
        queryString.append(" ORDER BY e.createdOn DESC");

        TypedQuery<Event> query = entityManager.createQuery(queryString.toString(), Event.class);

        if (userList != null && !userList.isEmpty()) {
            query.setParameter("users", userList);
        }
        if (stateList != null && !stateList.isEmpty()) {
            query.setParameter("states", stateList);
        }
        if (categoryList != null && !categoryList.isEmpty()) {
            query.setParameter("categories", categoryList);
        }
        if (rangeStart != null) {
            query.setParameter("rangeStart", rangeStart);
        }
        if (rangeEnd != null) {
            query.setParameter("rangeEnd", rangeEnd);
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    public List<Event> getEventsPublic(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Boolean onlyAvailable, int from, int size) {
        StringBuilder queryString = new StringBuilder("SELECT e FROM Event e WHERE e.state = :state");

        queryString.append(" AND e.state = :state");

        if (text != null && !text.isEmpty()) {
            queryString.append(" AND (LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%'))")
                    .append(" OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%')))");
        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            queryString.append(" AND e.category.id IN :categories");
        }
        if (paid != null) {
            queryString.append(" AND e.paid = :paid");
        }

        if (rangeStart != null) {
            queryString.append(" AND e.eventDate >= :rangeStart");
        } else {
            queryString.append(" AND e.eventDate > :currentDate");
        }

        if (rangeEnd != null) {
            queryString.append(" AND e.eventDate <= :rangeEnd");
        }

        if (onlyAvailable != null && onlyAvailable) {
            queryString.append(" AND (e.participantLimit = 0 OR e.participantLimit > e.confirmedRequests)");
        }

        TypedQuery<Event> query = entityManager.createQuery(queryString.toString(), Event.class);

        query.setParameter("state", State.PUBLISHED);
        if (text != null && !text.isEmpty()) {
            query.setParameter("text", text.toLowerCase());
        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            query.setParameter("categories", categoryIds);
        }
        if (paid != null) {
            query.setParameter("paid", paid);
        }

        if (rangeStart == null) {
            query.setParameter("currentDate", LocalDateTime.now());
        }

        if (rangeStart != null) {
            query.setParameter("rangeStart", rangeStart);
        }
        if (rangeEnd != null) {
            query.setParameter("rangeEnd", rangeEnd);
        }

        query.setFirstResult(from);
        query.setMaxResults(size);

        return query.getResultList();
    }
}
