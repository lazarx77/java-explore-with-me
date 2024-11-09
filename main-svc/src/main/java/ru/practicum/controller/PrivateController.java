package ru.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.service.EventService;
import ru.practicum.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;
import ru.practicum.validator.DateTimeValidator;
import ru.practicum.validator.LocationValidator;
import ru.practicum.validator.StringSizeValidator;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateController {

    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable
                                 @Positive(message = "id пользователя должен быть положительным") Long userId,
                                 @Valid @RequestBody NewEventDto dto) {
        log.info("Добавление нового события в PrivateController для пользователя с id {}", userId);
        DateTimeValidator.ValidateDatetime(dto.getEventDate());
        return eventService.addNewEvent(userId, dto);
    }

    @GetMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByUserId(@PathVariable @Positive(message = "id пользователя должен быть " +
            "положительным") Long userId,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение списка событий пользователя с id {}", userId);
        return eventService.getEventsByUserId(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByUserIdAndEventId(@PathVariable @Positive(message = "id пользователя должен быть " +
            "положительным") Long userId,
                                                   @PathVariable @Positive(message = "id события должен быть " +
                                                           "положительным") Long eventId) {
        log.info("Получение события с id {} пользователя с id {}", eventId, userId);
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventByUser(@PathVariable @Positive(message = "id пользователя должен быть " +
            "положительным") Long userId,
                                          @PathVariable @Positive(message = "id события должен быть " +
                                                  "положительным") Long eventId,
                                          @RequestBody UpdateEventUserRequest dto) {
        log.info("Обновление события с id {} пользователя с id {}", eventId, userId);
        if (dto.getAnnotation() != null) {
            StringSizeValidator.validateAnnotation(dto.getAnnotation());
        }
        if (dto.getDescription() != null) {
            StringSizeValidator.validateDescription(dto.getDescription());
        }
        if (dto.getTitle() != null) {
            StringSizeValidator.validateTitle(dto.getTitle());
        }
        if (dto.getEventDate() != null) {
            DateTimeValidator.ValidateDatetime(dto.getEventDate());
        }
        if (dto.getEventDate() != null) {
            DateTimeValidator.ValidateDatetime(dto.getEventDate());
        }
        if (dto.getLocation() != null) {
            LocationValidator.validateLocation(dto.getLocation());
        }
        if (dto.getParticipantLimit() != null && dto.getParticipantLimit() < 0) {
            throw new IllegalArgumentException("Лимит участников не может быть меньше 0");
        }
        return eventService.updateEventByUser(userId, eventId, dto);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addParticipationRequestToEvent(@PathVariable
                                                                  @Positive(message = "id пользователя должен быть " +
                                                                          "положительным") Long userId,
                                                                  @RequestParam
                                                                  @Positive(message = "id события должен быть " +
                                                                          "положительным")
                                                                  Long eventId) {
        log.info("Добавление запроса на участие в событии с id {} пользователя с id {}", eventId, userId);
        return requestService.addParticipationRequestToEvent(userId, eventId);
    }

    @GetMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequestsByUserId(@PathVariable
                                                                          @Positive(message = "id пользователя " +
                                                                                  "должен быть " +
                                                                                  "положительным") Long userId) {
        log.info("Получение запросов на участие в событиях пользователя с id {}", userId);
        return requestService.getParticipationRequestsByUserId(userId);
    }

    @PatchMapping("/{userId}/requests/{reqId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelRequestByUserIdAndEventId(@PathVariable
                                                                   @Positive(message = "id пользователя должен " +
                                                                           "быть положительным") Long userId,
                                                                   @PathVariable
                                                                   @Positive(message = "id запроса должен " +
                                                                           "быть положительным") Long reqId) {
        log.info("Отмена запроса на участие в событии с запросом reqId {} и userId {}", reqId, userId);
        return requestService.cancelRequest(userId, reqId);
    }


    @GetMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequests(@PathVariable
                                                                  @Positive(message = "id пользователя должен " +
                                                                          "быть положительным") Long userId,
                                                                  @PathVariable
                                                                  @Positive(message = "id события должен " +
                                                                          "быть положительным") Long eventId) {
        log.info("Получение запросов на участие в событии с eventId {} пользователя с userId {}", eventId, userId);
        return requestService.getParticipationRequestsByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResultDto updateRequestStatus(@PathVariable
                                                                 @Positive(message = "id пользователя должен " +
                                                                         "быть положительным") Long userId,
                                                                 @PathVariable
                                                                 @Positive(message = "id события должен " +
                                                                         "быть положительным") Long eventId,
                                                                 @RequestBody
                                                                 @Valid EventRequestStatusUpdateRequestDto dto) {
        log.info("Обновление статуса запроса на участие в событии с eventId {} и userId {}", eventId, userId);
        return requestService.updateRequestStatus(userId, eventId, dto);
    }
}
