package ru.practicum.request.mapper;

import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.Request;

public class ParticipationRequestDtoMapper {

    public static ParticipationRequestDto toParticipationRequestDto (Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .status(request.getStatus().toString())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .id(request.getId())
                .build();
    }
}
