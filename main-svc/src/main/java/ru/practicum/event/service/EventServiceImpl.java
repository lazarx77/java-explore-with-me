package ru.practicum.event.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventDtoMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.StateActionException;
import ru.practicum.exception.StatePublishedException;
import ru.practicum.stats_client.client.StatsClient;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.validator.DateTimeValidator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Реализация сервиса для работы с событиями.
 */
@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final StatsClient statsClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public EventFullDto addNewEvent(Long userId, NewEventDto dto) {
        log.info("Получен запрос на добавление нового события: {}", dto);
        Event event = EventDtoMapper.mapToEvent(dto, categoryRepository);
        log.info("Проверка на существование пользователя с id: {}", userId);
        event.setInitiator(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + userId + "не найден")));
        log.info("Событие передается на апись в БД.");
        return EventDtoMapper.toEventFullDto(eventRepository.save(event), statsClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, int from, int size) {
        log.info("Получен запрос на получение событий пользователя с id: {}", userId);
        log.info("Проверка на существование пользователя с id: {}", userId);
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь с таким id: " + userId + " не найден");
        }
        log.info("Формирование списка событий.");
        return eventRepository.findAllByInitiatorId(userId, from, size)
                .stream()
                .map(event -> EventDtoMapper.toEventShortDto(event, statsClient))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        log.info("Получен запрос на получение событий пользователя с userId: {} и eventId: {}", userId, eventId);
        log.info("Проверка на существование пользователя с id: " + userId);

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + userId + " не найден."));

        log.info("Проверка на существование события с eventId: {} у пользователя с userId: {}", eventId, userId);
        return EventDtoMapper.toEventFullDto(eventRepository.findByInitiatorIdAndId(userId, eventId)
                        .orElseThrow(() -> new NotFoundException("Событие с таким eventId: " + eventId +
                                " у пользователя с userId: " + userId + " не найдено.")),
                statsClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest dto) {
        log.info("Получен запрос на обновление события с userId: {} и eventId: {}", userId, eventId);
        log.info("Проверка на существование пользователя с id: " + userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + userId + " не найден."));
        log.info("Проверка на существование события с eventId: {} у пользователя с userId: {}", eventId, userId);
        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Событие с таким eventId: " + eventId +
                        " у пользователя с userId: " + userId + " не найдено."));

        if (event.getState().equals(State.PUBLISHED)) {
            throw new StatePublishedException("Событие с eventId: " + eventId +
                    " уже опубликовано (PUBLISHED). Изменение возможно только для отмененных (CANCELED) или ожидающих" +
                    " модерации (PENDING) событий.");
        }
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Категория с таким id: " + dto.getCategory() +
                            " не найдена.")));
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }

        if (dto.getStateAction() != null) {
            StateAction stateAction = StateAction.from(dto.getStateAction())
                    .orElseThrow(() -> new StateActionException("Неизвестное действие: " + dto.getStateAction() +
                            ". Возможные пользователю действии: CANCEL_REVIEW, SEND_TO_REVIEW."));
            if (stateAction.equals(StateAction.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            } else if (stateAction.equals(StateAction.CANCEL_REVIEW)) {
                event.setState(State.CANCELED);
            }
        }
        log.info("Обновление пользователем события с id: {}.", event.getId());

        return EventDtoMapper.toEventFullDto(eventRepository.save(event), statsClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest dto) {
        log.info("Получен запрос на обновление события с eventId: {}", eventId);
        log.info("Проверка на существование события с eventId: {}", eventId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с таким eventId: " + eventId + " не найдено."));

        DateTimeValidator.validateDateTimeOnUpdate(event.getEventDate());

        if (!event.getState().equals(State.PENDING)) {
            throw new StatePublishedException("Событие с eventId: " + eventId +
                    " уже опубликовано (PUBLISHED) или отклонено(CANCELLED). Изменение возможно администратором" +
                    " только для ожидающих модерации (PENDING) событий.");
        }

        if (dto.getStateAction() != null) {
            StateAction stateAction = StateAction.from(dto.getStateAction())
                    .orElseThrow(() -> new StateActionException("Некорректное действие StateAction: " +
                            dto.getStateAction() + ". Возможные администратору действия: PUBLISH_EVENT," +
                            " REJECT_EVENT."));

            if (stateAction.equals(StateAction.PUBLISH_EVENT)) {
                event.setPublishedOn(LocalDateTime.now());
                event.setState(State.PUBLISHED);
            } else if (stateAction.equals(StateAction.REJECT_EVENT)) {
                event.setState(State.CANCELED);
            }
        }

        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategory(categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Категория с таким id: " + dto.getCategory() +
                            " не найдена.")));
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }

        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        log.info("Обновленное событие с id: {} администратором направлено на сохренение в БД.", event.getId());
        return EventDtoMapper.toEventFullDto(eventRepository.save(event), statsClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventFullDto> getEventsByAdmin(Long[] users, String[] states, Long[] categories,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd, int from, int size) {

        List<Long> userList = (users != null) ? Arrays.asList(users) : null;

        List<String> stateList = (states != null) ? Arrays.stream(states)
                .map(State::from)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(State::toString)
                .toList()
                : null;

        List<Long> categoryList = (categories != null) ? Arrays.asList(categories) : null;

        List<Event> events = eventRepository.getEvents(userList, stateList, categoryList, rangeStart, rangeEnd,
                from, size);

        return events.stream()
                .map(event -> EventDtoMapper.toEventFullDto(event, statsClient))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    public EventFullDto getEventPublic(Long eventId) {
        log.info("Получен запрос на получение события с eventId: {}", eventId);
        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Событие с таким eventId: " + eventId + " и статусом " +
                        State.PUBLISHED + " не найдено."));
        return EventDtoMapper.toEventFullDto(event, statsClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EventShortDto> getEventsPublic(String text, Long[] categories, Boolean paid, LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd, Boolean onlyAvailable, String sortToUpperCase,
                                               int from, int size) {

        List<Long> categoryIds = (categories != null) ? Arrays.asList(categories) : null;

        List<Event> events = eventRepository.getEventsPublic(text, categoryIds, paid, rangeStart, rangeEnd,
                onlyAvailable, from, size);


        List<EventShortDto> eventsDtoList = events.stream()
                .map(event -> EventDtoMapper.toEventShortDto(event, statsClient))
                .collect(Collectors.toList());

        if (sortToUpperCase != null) {
            if (sortToUpperCase.equals("EVENT_DATE")) {
                eventsDtoList.sort(Comparator.comparing(EventShortDto::getEventDate));
            } else if (sortToUpperCase.equals("VIEWS")) {
                eventsDtoList.sort(Comparator.comparing(EventShortDto::getViews));
            }
        }

        return eventsDtoList;
    }
}
