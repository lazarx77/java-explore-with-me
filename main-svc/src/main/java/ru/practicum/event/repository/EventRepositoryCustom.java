package ru.practicum.event.repository;

import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {
    List<Event> getEvents(List<Long> userList,
                          List<String> stateList,
                          List<Long> categoryList,
                          LocalDateTime rangeStart,
                          LocalDateTime rangeEnd,
                          int offset,
                          int limit);
}
