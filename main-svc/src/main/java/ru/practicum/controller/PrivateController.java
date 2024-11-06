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
import ru.practicum.validator.DateTimeValidator;
import ru.practicum.validator.LocationValidator;
import ru.practicum.validator.StringSizeValidator;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping
@Validated
public class PrivateController {

    private final EventService eventService;

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable
                                 @Positive(message = "id пользователя должен быть положительным") Long userId,
                                 @Valid @RequestBody NewEventDto dto) {
        log.info("Добавление нового события в PrivateController для пользователя с id {}", userId);
        DateTimeValidator.ValidateDatetime(dto.getEventDate());
        return eventService.addNewEvent(userId, dto);
    }

    @GetMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByUserId(@PathVariable @Positive(message = "id пользователя должен быть " +
            "положительным") Long userId,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение списка событий пользователя с id {}", userId);
        return eventService.getEventsByUserId(userId, from, size);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByUserIdAndEventId(@PathVariable @Positive(message = "id пользователя должен быть " +
            "положительным") Long userId,
                                                   @PathVariable @Positive(message = "id события должен быть " +
                                                           "положительным") Long eventId) {
        log.info("Получение события с id {} пользователя с id {}", eventId, userId);
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
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
        return eventService.updateEventByUser(userId, eventId, dto);
    }

}
