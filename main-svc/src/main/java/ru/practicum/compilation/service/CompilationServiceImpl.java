package ru.practicum.compilation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.compilation.mapper.CompilationDtoMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.CompilationTitleConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.stats_client.client.StatsClient;

import java.util.List;

/**
 * Реализация сервиса для работы с компиляциями событий.
 */
@Service
@AllArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final StatsClient statsClient;
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    /**
     * {@inheritDoc}
     */
    public CompilationDtoResponse createCompilation(NewCompilationDto dto) {
        log.info("Вызывается метод createCompilation в CompilationServiceImpl");
        log.info("Проверка на наличие подборки с названием /" + dto.getTitle() + "/.");
        compilationRepository.findByTitle(dto.getTitle()).ifPresent(compilation -> {
            throw new CompilationTitleConflictException("Подборка событий с названием /" + dto.getTitle() +
                    "/ уже существует");
        });

        List<Event> events = eventRepository.findAllByIds(dto.getEvents());

        Compilation compilation = Compilation.builder()
                .events(events)
                .title(dto.getTitle())
                .pinned(dto.getPinned() != null ? dto.getPinned() : false)
                .build();
        return CompilationDtoMapper.toCompilationDtoResponse(compilationRepository.save(compilation), statsClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompilationDtoResponse updateCompilation(Long compId, UpdateCompilationRequestDto dto) {
        log.info("Вызывается метод updateCompilation в CompilationServiceImpl");
        log.info("Проверка на наличие подборки с id /" + compId + "/.");
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка событий с id /" + compId + "/ не найдена"));

        List<Event> events = eventRepository.findAllByIds(dto.getEvents());

        if (dto.getEvents() != null) {
            compilation.setEvents(events);
        }
        if (dto.getTitle() != null) {
            if (compilationRepository.findByTitle(dto.getTitle()).isPresent()) {
                throw new CompilationTitleConflictException("Подборка событий с названием /" + dto.getTitle() +
                        "/ уже существует");
            }
            compilation.setTitle(dto.getTitle());
        }
        if (dto.getPinned() != null) {
            compilation.setPinned(dto.getPinned());
        }
        return CompilationDtoMapper
                .toCompilationDtoResponse(compilationRepository.save(compilation), statsClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCompilation(Long compId) {
        log.info("Вызывается метод deleteCompilation в CompilationServiceImpl");
        log.info("Проверка на наличие подборки с id /" + compId + "/.");
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка событий с id /" + compId + "/ не найдена"));
        compilationRepository.delete(compilation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CompilationDtoResponse> getCompilationsPublic(Boolean pinned, int from, int size) {
        log.info("Вызывается метод getCompilationsPublic в CompilationServiceImpl");
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.getCompilationsPublic(from, size);
        } else {
            compilations = compilationRepository.getCompilationsPublicPinned(pinned, from, size);
        }
        return compilations.stream()
                .map(compilation -> CompilationDtoMapper.toCompilationDtoResponse(compilation, statsClient))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompilationDtoResponse getCompilationPublic(Long compId) {
        log.info("Вызывается метод getCompilationPublic в CompilationServiceImpl");
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка событий с id /" + compId + "/ не найдена"));
        return CompilationDtoMapper.toCompilationDtoResponse(compilation, statsClient);
    }
}
