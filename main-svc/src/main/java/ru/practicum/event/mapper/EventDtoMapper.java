package ru.practicum.event.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import ru.practicum.category.mapper.CategoryDtoMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.dto.admin_comment.AdminCommentResponse;
import ru.practicum.event.dto.admin_comment.PrivateEventFullDto;
import ru.practicum.event.dto.event.EventFullDto;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.dto.event.NewEventDto;
import ru.practicum.event.model.admin_comment.AdminComment;
import ru.practicum.event.model.event.Event;
import ru.practicum.event.model.event.State;
import ru.practicum.exception.NotFoundException;
import ru.practicum.stats_client.client.StatsClient;
import ru.practicum.user.mapper.UserDtoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.practicum.util.Utils.formatter;

/**
 * Mapper для преобразования объектов события между различными представлениями (DTO) и сущностями.
 * Содержит методы для преобразования новых событий, полных событий и кратких событий.
 */
@AllArgsConstructor
public class EventDtoMapper {

    /**
     * Преобразует NewEventDto в сущность Event.
     *
     * @param dto                объект NewEventDto, содержащий данные нового события.
     * @param categoryRepository репозиторий категорий для получения категории события.
     * @return сущность Event, созданная на основе данных из dto.
     */
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

    /**
     * Преобразует сущность Event в EventFullDto.
     *
     * @param event       сущность события.
     * @param statsClient клиент для получения статистики просмотров.
     * @return объект EventFullDto, содержащий полную информацию о событии.
     */
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
                .views(getViews(statsClient, event.getId()))
                .build();
    }

    /**
     * Преобразует сущность Event в EventShortDto.
     *
     * @param event       сущность события.
     * @param statsClient клиент для получения статистики просмотров.
     * @return объект EventShortDto, содержащий краткую информацию о событии.
     */
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
                .views(getViews(statsClient, event.getId()))
                .build();
    }

    public static PrivateEventFullDto toPrivateEventFullDtoWithViews(Event event, StatsClient statsClient,
                                                                     List<AdminComment> ac) {
        List<AdminCommentResponse> shortAdminComment = ac.stream()
                .map(AdminCommentDtoMapper::toAdminCommentResponse)
                .toList();

        return PrivateEventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserDtoMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .state(event.getState())
                .views(getViews(statsClient, event.getId()))
                .adminComments(shortAdminComment)
                .build();
    }

    private static long getViews(StatsClient statsClient, Long eventId) {
        String[] uris = {"/events/" + eventId};
        ResponseEntity<Object> response = statsClient.getStats(LocalDateTime.now().minusYears(30).format(formatter),
                LocalDateTime.now().format(formatter), uris, true);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> statsResponseList = objectMapper.convertValue(response.getBody(),
                new TypeReference<>() {
                });

        long views = 0L;

        if (statsResponseList != null && !statsResponseList.isEmpty()) {
            views = ((Number) statsResponseList.getFirst().get("hits")).longValue();
        }

        return views;
    }
}
