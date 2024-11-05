package ru.practicum.event.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;

@Transactional(readOnly = true)
public interface EventService {

    @Transactional
    EventFullDto addNewEvent(Long userId, NewEventDto dto);
}
