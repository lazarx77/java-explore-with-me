package ru.practicum.compilation.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;

@Transactional(readOnly = true)
public interface CompilationService {

    @Transactional
    Compilation createCompilation(NewCompilationDto dto);
}
