package ru.practicum.event.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import ru.practicum.category.mapper.CategoryDtoMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.exception.NotFoundException;
import ru.practicum.stats_client.client.StatsClient;
import ru.practicum.user.mapper.UserDtoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.practicum.util.DateTimeUtil.formatter;

@AllArgsConstructor
public class EventDtoMapper {

    public static Event mapToEvent(NewEventDto dto, CategoryRepository categoryRepository) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(categoryRepository.findById(dto.getCategory())
                        .orElseThrow(() -> new NotFoundException("Категория с id:" + dto.getCategory() +
                                "не найдена.")))
                .confirmedRequests(0L)
                .createdOn(LocalDateTime.now())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .location(dto.getLocation())
                .paid(dto.getPaid() != null ? dto.getPaid() : false)
                .participantLimit(dto.getParticipantLimit() != null ? dto.getParticipantLimit() : 0)
                .requestModeration(dto.getRequestModeration() != null ? dto.getRequestModeration() : true)
                .state(State.PENDING)
                .title(dto.getTitle())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event, StatsClient statsClient) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(UserDtoMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(getViews(statsClient, event))
                .build();
    }

    public static EventShortDto toEventShortDto(Event event, StatsClient statsClient) {

        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryDtoMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserDtoMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(getViews(statsClient, event))
                .build();
    }

    private static long getViews(StatsClient statsClient, Event event) {
        String[] uris = {"/events/" + event.getId()};
        ResponseEntity<Object> response = statsClient.getStats(LocalDateTime.now().minusYears(30).format(formatter),
                LocalDateTime.now().format(formatter), uris, false);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> statsResponseList = objectMapper.convertValue(response.getBody(),
                new TypeReference<>() {
                });

        long views;

        if (statsResponseList != null && !statsResponseList.isEmpty()) {
            views = ((Number) statsResponseList.getFirst().get("hits")).longValue();
        } else {
            views = 0L;
        }

        return views;
    }
}
