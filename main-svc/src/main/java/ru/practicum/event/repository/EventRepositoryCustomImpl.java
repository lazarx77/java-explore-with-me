package ru.practicum.event.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;

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
}
