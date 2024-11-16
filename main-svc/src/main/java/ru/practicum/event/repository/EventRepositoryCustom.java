package ru.practicum.event.repository;

import ru.practicum.event.model.event.Event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Пользовательский репозиторий для работы с событиями.
 * Содержит методы для получения событий с учетом различных фильтров.
 */
public interface EventRepositoryCustom {

    /**
     * Получает события по списку пользователей, состояниям, категориям и диапазону дат.
     *
     * @param userList     список идентификаторов пользователей.
     * @param stateList    список состояний событий.
     * @param categoryList список идентификаторов категорий.
     * @param rangeStart   начало диапазона дат.
     * @param rangeEnd     конец диапазона дат.
     * @param offset       смещение для пагинации.
     * @param limit        лимит количества возвращаемых событий.
     * @return список событий, соответствующих заданным критериям.
     */
    List<Event> getEvents(List<Long> userList,
                          List<String> stateList,
                          List<Long> categoryList,
                          LocalDateTime rangeStart,
                          LocalDateTime rangeEnd,
                          int offset,
                          int limit);

    /**
     * Получает публичные события по заданным критериям.
     *
     * @param text          текст для поиска в событиях.
     * @param categoryIds   список идентификаторов категорий.
     * @param paid          флаг, указывающий, являются ли события платными.
     * @param rangeStart    начало диапазона дат.
     * @param rangeEnd      конец диапазона дат.
     * @param onlyAvailable флаг, указывающий, возвращать ли только доступные события.
     * @param from          смещение для пагинации.
     * @param size          лимит количества возвращаемых событий.
     * @return список публичных событий, соответствующих заданным критериям.
     */
    List<Event> getEventsPublic(String text,
                                List<Long> categoryIds,
                                Boolean paid,
                                LocalDateTime rangeStart,
                                LocalDateTime rangeEnd,
                                Boolean onlyAvailable,
                                int from,
                                int size);
}
