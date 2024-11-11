package ru.practicum.request.mapper;

import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.Request;

/**
 * Mapper для преобразования объектов типа Request в ParticipationRequestDto.
 */
public class ParticipationRequestDtoMapper {

    /**
     * Преобразует объект Request в ParticipationRequestDto.
     *
     * @param request Объект запроса, который необходимо преобразовать.
     * @return Преобразованный объект ParticipationRequestDto.
     */
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .status(request.getStatus().toString())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .id(request.getId())
                .build();
    }
}
