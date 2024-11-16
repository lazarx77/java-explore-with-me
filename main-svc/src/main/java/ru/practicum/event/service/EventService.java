package ru.practicum.event.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.admin_comment.PrivateEventFullDto;
import ru.practicum.event.dto.event.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с событиями.
 * Содержит методы для добавления, обновления и получения событий.
 */
@Transactional(readOnly = true)
public interface EventService {

    /**
     * Добавляет новое событие.
     *
     * @param userId идентификатор пользователя, создающего событие.
     * @param dto    данные нового события.
     * @return EventFullDto - полная информация о добавленном событии.
     */
    @Transactional
    EventFullDto addNewEvent(Long userId, NewEventDto dto);

    /**
     * Получает события по идентификатору пользователя с пагинацией.
     *
     * @param userId идентификатор пользователя.
     * @param from   смещение для пагинации.
     * @param size   лимит количества возвращаемых событий.
     * @return список краткой информации о событиях.
     */
    List<EventShortDto> getEventsByUserId(Long userId, int from, int size);

    /**
     * Получает событие по идентификатору пользователя и события.
     *
     * @param userId  идентификатор пользователя.
     * @param eventId идентификатор события.
     * @return EventFullDto - полная информация о событии.
     */
    PrivateEventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    /**
     * Обновляет событие пользователем.
     *
     * @param userId  идентификатор пользователя.
     * @param eventId идентификатор события.
     * @param dto     данные для обновления события.
     * @return EventFullDto - полная информация об обновленном событии.
     */
    @Transactional
    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest dto);

    /**
     * Обновляет событие администратором.
     *
     * @param eventId идентификатор события.
     * @param dto     данные для обновления события.
     * @return EventFullDto - полная информация об обновленном событии.
     */
    @Transactional
    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest dto);

    /**
     * Получает события по фильтрам для администраторов.
     *
     * @param users      список идентификаторов пользователей.
     * @param states     список состояний событий.
     * @param categories список идентификаторов категорий.
     * @param rangeStart начало диапазона дат.
     * @param rangeEnd   конец диапазона дат.
     * @param from       смещение для пагинации.
     * @param size       лимит количества возвращаемых событий.
     * @return список полной информации о событиях.
     */
    List<EventFullDto> getEventsByAdmin(Long[] users, String[] states, Long[] categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    /**
     * Получает публичное событие по идентификатору.
     *
     * @param eventId идентификатор события.
     * @return EventFullDto - полная информация о публичном событии.
     */
    EventFullDto getEventPublic(Long eventId);

    /**
     * Получает публичные события по заданным критериям.
     *
     * @param text            текст для поиска в событиях.
     * @param categories      список идентификаторов категорий.
     * @param paid            флаг, указывающий, являются ли события платными.
     * @param rangeStart      начало диапазона дат.
     * @param rangeEnd        конец диапазона дат.
     * @param onlyAvailable   флаг, указывающий, возвращать ли только доступные события.
     * @param sortToUpperCase параметр сортировки.
     * @param from            смещение для пагинации.
     * @param size            лимит количества возвращаемых событий.
     * @return список краткой информации о публичных событиях.
     */
    List<EventShortDto> getEventsPublic(String text, Long[] categories, Boolean paid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Boolean onlyAvailable, String sortToUpperCase,
                                        int from, int size);

    /**
     * Получает список ожидающих событий для администраторов.
     *
     * @return список объектов {@link PrivateEventFullDto}, представляющих
     *         ожидающие события. Возвращает пустой список, если таких событий нет.
     */
    List<PrivateEventFullDto> getPendingEventsByAdmin();
}
