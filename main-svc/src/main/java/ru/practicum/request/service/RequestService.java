package ru.practicum.request.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

@Transactional(readOnly = true)
public interface RequestService {

    @Transactional
    ParticipationRequestDto addParticipationRequestToEvent(Long userId, Long eventId);

    List<ParticipationRequestDto> getParticipationRequestsByUserId(Long userId);

    @Transactional
    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getParticipationRequestsByUserIdAndEventId(Long userId, Long eventId);

    @Transactional
    EventRequestStatusUpdateResultDto updateRequestStatus(Long userId, Long eventId,
                                                                EventRequestStatusUpdateRequestDto dto);
}
