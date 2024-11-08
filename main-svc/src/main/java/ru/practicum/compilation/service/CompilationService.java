package ru.practicum.compilation.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.compilation.model.Compilation;

import java.util.List;

@Transactional(readOnly = true)
public interface CompilationService {

    @Transactional
    CompilationDtoResponse createCompilation(NewCompilationDto dto);

    @Transactional
    CompilationDtoResponse updateCompilation(Long compId, UpdateCompilationRequestDto dto);

    @Transactional
    void deleteCompilation(Long compId);

    List<CompilationDtoResponse> getCompilationsPublic(Boolean pinned, int from, int size);

    CompilationDtoResponse getCompilationPublic(Long compId);
}
