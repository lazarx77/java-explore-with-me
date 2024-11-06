package ru.practicum.event.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.*;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface EventService {

    @Transactional
    EventFullDto addNewEvent(Long userId, NewEventDto dto);

    List<EventShortDto> getEventsByUserId(Long userId, int from, int size);

    EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    @Transactional
    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest dto);

    @Transactional
    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest dto);

    List<EventFullDto> getEventsByAdmin(Long[] users, String[] states, Long[] categories, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, int from, int size);
}
