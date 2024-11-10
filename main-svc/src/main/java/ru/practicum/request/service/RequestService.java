package ru.practicum.request.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Сервис для работы с запросами на участие в событиях.
 * Предоставляет методы для добавления, получения и обновления запросов.
 */
@Transactional(readOnly = true)
public interface RequestService {

    /**
     * Добавляет запрос на участие в событии.
     *
     * @param userId  Идентификатор пользователя, который делает запрос.
     * @param eventId Идентификатор события, в которое пользователь хочет участвовать.
     * @return DTO запроса на участие.
     */
    @Transactional
    ParticipationRequestDto addParticipationRequestToEvent(Long userId, Long eventId);

    /**
     * Получает все запросы на участие, сделанные пользователем с указанным идентификатором.
     *
     * @param userId Идентификатор пользователя.
     * @return Список DTO запросов на участие.
     */
    List<ParticipationRequestDto> getParticipationRequestsByUserId(Long userId);

    /**
     * Получает запросы на участие пользователя в конкретном событии.
     *
     * @param userId  Идентификатор пользователя.
     * @param eventId Идентификатор события.
     * @return Список DTO запросов на участие.
     */
    List<ParticipationRequestDto> getParticipationRequestsByUserIdAndEventId(Long userId, Long eventId);

    /**
     * Обновляет статус запроса на участие в событии.
     *
     * @param userId  Идентификатор пользователя, который обновляет статус.
     * @param eventId Идентификатор события, к которому относится запрос.
     * @param dto     DTO с обновленным статусом запроса.
     * @return Результат обновления статуса запросов.
     */
    @Transactional
    EventRequestStatusUpdateResultDto updateRequestStatus(Long userId, Long eventId,
                                                          EventRequestStatusUpdateRequestDto dto);

    /**
     * Отменяет запрос на участие в событии.
     *
     * @param requesterId Идентификатор пользователя, который отменяет запрос.
     * @param reqId       Идентификатор запроса на участие.
     * @return DTO отмененного запроса на участие.
     */
    @Transactional
    ParticipationRequestDto cancelRequest(Long requesterId, Long reqId);
}
