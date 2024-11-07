package ru.practicum.compilation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;

@Service
@AllArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    CompilationRepository compilationRepository;

    public Compilation createCompilation(NewCompilationDto dto) {

    }
}
