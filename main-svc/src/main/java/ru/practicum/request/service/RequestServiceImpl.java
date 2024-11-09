package ru.practicum.request.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.RequestConflictException;
import ru.practicum.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.ParticipationRequestDtoMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.Status;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto addParticipationRequestToEvent(Long userId, Long eventId) {
        log.info("Вызывается метод addParticipationRequestToEvent в RequestServiceImpl");
        log.info("Проверка на существование пользователя с id {}", userId);
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + userId + "  не найден"));
        log.info("Проверка на существование события с id {}", eventId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с таким id: " + eventId + " не найдено"));
        if (requestRepository.findByRequesterIdAndEventId(userId, event.getId()).isPresent()) {
            throw new RequestConflictException("Пользователь userId: " + userId + " уже подал запрос на участие в " +
                    "этом событии eventId: " + event.getId() + ".");
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new RequestConflictException("Пользователь userId: " + userId + " не может добавить запрос на " +
                    "участие в событии eventId: " + event.getId() + ", так как он инициатор события.");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new RequestConflictException("Пользователь userId: " + userId + " не может добавить запрос на " +
                    "участие в событии eventId: " + event.getId() + ", так как оно не опубликовано (PUBLISHED). " +
                    "актуальный статус события: " + event.getState());
        }
        if (event.getParticipantLimit() > 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new RequestConflictException("Пользователь userId: " + userId + " не может добавить запрос на " +
                    "участие в событии eventId: " + event.getId() + ", так как достигнут лимит запросов на участие.");
        }
        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(requester)
                .build();
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
            if (event.getConfirmedRequests() == null) {
                event.setConfirmedRequests(1L);
            } else {
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            }
            eventRepository.save(event);
        } else {
            request.setStatus(Status.PENDING);
        }
        return ParticipationRequestDtoMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequestsByUserId(Long userId) {
        log.info("Вызывается метод getParticipationRequestsByUserId в RequestServiceImpl");
        log.info("Проверка на существование пользователя с id {}", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + userId + "  не найден"));
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(ParticipationRequestDtoMapper::toParticipationRequestDto)
                .toList();
    }

//    @Override
//    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
//        log.info("Вызывается метод cancelParticipationRequest в RequestServiceImpl");
//        log.info("Проверка на существование пользователя с id {}", userId);
//        userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("Пользователь с таким userId: " + userId + "  не найден"));
//        log.info("Проверка на существование запроса с id {}", requestId);
//        Request request = requestRepository.findById(requestId)
//                .orElseThrow(() -> new NotFoundException("Запрос с таким requestId: " + requestId + " не найден"));
//        if (!request.getRequester().getId().equals(userId)) {
//            log.info("Пользователь userId: " + userId + " не может отменить запрос на участие в событии eventId: " +
//                    "участие в событии eventId: " + request.getEvent().getId() + ", так как он не является его" +
//                    " инициатором.");
//            throw new NotFoundException("Пользователь userId: " + userId + " не может отменить запрос на " +
//                    "участие в событии eventId: " + request.getEvent().getId() + ", так как он не является его" +
//                    " инициатором.");
//        }
//        request.setStatus(Status.PENDING);
//        Event event = eventRepository.findById(request.getEvent().getId()).orElseThrow(() ->
//                new NotFoundException("Событие с таким id: " + request.getEvent().getId() + " не найдено"));
//        event.setConfirmedRequests(event.getConfirmedRequests() - 1);
//        eventRepository.save(event);
//        return ParticipationRequestDtoMapper.toParticipationRequestDto(requestRepository.save(request));
//    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequestsByUserIdAndEventId(Long userId, Long eventId) {
        log.info("Вызывается метод getParticipationRequestsByEventId в RequestServiceImpl");
        log.info("Проверка на существование пользователя с id {}", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + userId + "  не найден"));
        log.info("Проверка на существование события с id {}", eventId);
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с таким id: " + eventId + " не найдено"));

        return requestRepository.findAllByInitiatorIdAndEventId(userId, eventId).stream()
                .map(ParticipationRequestDtoMapper::toParticipationRequestDto)
                .toList();
    }

    @Override
    public EventRequestStatusUpdateResultDto updateRequestStatus(Long userId, Long eventId,
                                                                 EventRequestStatusUpdateRequestDto dto) {
        log.info("Вызывается метод updateRequestStatus в RequestServiceImpl");
        log.info("Проверка на существование пользователя с id {}", userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + userId + "  не найден"));
        log.info("Проверка на существование события в updateRequestStatus с eventId: {}", eventId);
        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId).orElseThrow(() ->
                new NotFoundException("Событие с таким eventId: " + eventId + " у пользователя с userId: " + userId +
                        " не существует."));
        List<Long> requestIds = dto.getRequestIds();
        Status requestedStatus = dto.getStatus();

        List<Request> requestsToUpdate = requestRepository.findAllByIdsAndEventId(requestIds, eventId);
        requestsToUpdate.forEach(request -> {
            if (!request.getStatus().equals(Status.PENDING)) {
                throw new RequestConflictException("Статус запроса requestId: " + request.getId() +
                        " должен быть PENDING");
            }

            if (requestedStatus.equals(Status.CONFIRMED)) {
                if (event.getConfirmedRequests() != null &&
                        event.getConfirmedRequests() >= event.getParticipantLimit()) {
                    throw new RequestConflictException("Событие с eventId: " + eventId + " уже заполнено");
                }
                Status statusToSet = Status.CONFIRMED;
                Long eventConfirmedCount = event.getConfirmedRequests();
                if (event.getParticipantLimit() > 0) {
                    if (event.getConfirmedRequests() == null) {
                        eventConfirmedCount = 1L;
                    } else if (event.getConfirmedRequests() < event.getParticipantLimit()) {
                        eventConfirmedCount = event.getConfirmedRequests() + 1;
                    } else {
                        statusToSet = Status.REJECTED;
                    }
                } else {
                    if (event.getConfirmedRequests() == null) {
                        eventConfirmedCount = 1L;
                    } else {
                        eventConfirmedCount = event.getConfirmedRequests() + 1;
                    }
                }
                request.setStatus(statusToSet);
                event.setConfirmedRequests(eventConfirmedCount);
                eventRepository.save(event);
            } else if (requestedStatus.equals(Status.REJECTED)) {
                request.setStatus(Status.REJECTED);
            } else {
                throw new RequestConflictException("Статус запроса должен быть CONFIRMED или REJECTED");
            }
        });

        List<ParticipationRequestDto> confirmed = requestsToUpdate.stream()
                .filter(request -> request.getStatus().equals(Status.CONFIRMED))
                .map(ParticipationRequestDtoMapper::toParticipationRequestDto)
                .toList();

        List<ParticipationRequestDto> rejected = requestsToUpdate.stream()
                .filter(request -> request.getStatus().equals(Status.REJECTED))
                .map(ParticipationRequestDtoMapper::toParticipationRequestDto)
                .toList();

        return new EventRequestStatusUpdateResultDto(confirmed, rejected);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long requesterId, Long reqId) {
        log.info("Вызывается метод cancelRequest в RequestServiceImpl");

        log.info("Проверка на существование пользователя с id {}", requesterId);
        userRepository.findById(requesterId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id: " + requesterId + "  не найден"));

        log.info("Проверка на существование запроса с id {} c его реквестером {}", reqId, requesterId);
        Request request = requestRepository.findByIdAndRequesterId(reqId, requesterId).orElseThrow(() ->
                new NotFoundException("Запрос с таким id " + reqId + " у пользователя с userId: " + requesterId +
                        " не существует."));
        log.info("Запрос:{} найден в БД", request);

        if (request.getStatus().equals(Status.CONFIRMED)) {
            Event event = request.getEvent();
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }
        request.setStatus(Status.CANCELED);
        log.info("Запрос:{} передается на запись в БД", request);
        return ParticipationRequestDtoMapper.toParticipationRequestDto(requestRepository.save(request));
    }
}
