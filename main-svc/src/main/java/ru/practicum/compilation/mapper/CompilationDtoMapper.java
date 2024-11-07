package ru.practicum.compilation.mapper;

import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;

public class CompilationDtoMapper {

    public static Compilation toCompilation(NewCompilationDto compilationDto) {

        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .build();
    }
}
