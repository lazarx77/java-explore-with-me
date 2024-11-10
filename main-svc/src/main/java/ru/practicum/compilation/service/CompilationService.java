package ru.practicum.compilation.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;

import java.util.List;

/**
 * Сервис для работы с компиляциями событий.
 * <p>
 * Этот интерфейс определяет методы для создания, обновления, удаления и получения компиляций.
 * Все методы, кроме методов получения, помечены аннотацией @Transactional, что означает,
 * что они будут выполняться в рамках транзакции.
 */
@Transactional(readOnly = true)
public interface CompilationService {

    /**
     * Создает новую компиляцию.
     *
     * @param dto объект NewCompilationDto, содержащий данные для создания компиляции.
     * @return CompilationDtoResponse - созданная компиляция с её данными.
     */
    @Transactional
    CompilationDtoResponse createCompilation(NewCompilationDto dto);

    /**
     * Обновляет существующую компиляцию.
     *
     * @param compId идентификатор компиляции, которую необходимо обновить.
     * @param dto    объект UpdateCompilationRequestDto, содержащий обновленные данные компиляции.
     * @return CompilationDtoResponse - обновленная компиляция с её данными.
     */
    @Transactional
    CompilationDtoResponse updateCompilation(Long compId, UpdateCompilationRequestDto dto);

    /**
     * Удаляет компиляцию по её идентификатору.
     *
     * @param compId идентификатор компиляции, которую необходимо удалить.
     */
    @Transactional
    void deleteCompilation(Long compId);

    /**
     * Получает список компиляций с поддержкой пагинации.
     *
     * @param pinned статус закрепления компиляции (true для закрепленных).
     * @param from   индекс, с которого начинается выборка компиляций.
     * @param size   максимальное количество компиляций, которые необходимо вернуть.
     * @return List<CompilationDtoResponse> - список компиляций.
     */
    List<CompilationDtoResponse> getCompilationsPublic(Boolean pinned, int from, int size);


    /**
     * Получает компиляцию по её идентификатору.
     *
     * @param compId идентификатор компиляции, которую необходимо получить.
     * @return CompilationDtoResponse - компиляция с указанным идентификатором.
     */
    CompilationDtoResponse getCompilationPublic(Long compId);
}
