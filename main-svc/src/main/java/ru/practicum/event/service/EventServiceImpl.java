package ru.practicum.event.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.mapper.EventDtoMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public EventFullDto addNewEvent(Long userId, NewEventDto dto) {
        Event event = EventDtoMapper.mapToEvent(dto, categoryRepository);
        event.setInitiator(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + userId + "не найден")));
        return EventDtoMapper.toEventFullDto(eventRepository.save(event));
    }
}
