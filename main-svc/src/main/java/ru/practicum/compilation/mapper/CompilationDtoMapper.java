package ru.practicum.compilation.mapper;

import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.mapper.EventDtoMapper;
import ru.practicum.stats_client.client.StatsClient;

public class CompilationDtoMapper {

//    public static Compilation toCompilation(NewCompilationDto compilationDto) {
//
//        return Compilation.builder()
//                .title(compilationDto.getTitle())
//                .pinned(compilationDto.getPinned())
//                .build();
//    }

    public static CompilationDtoResponse toCompilationDtoResponse(Compilation compilation, StatsClient statsClient) {
        return CompilationDtoResponse.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream()
                        .map(event -> EventDtoMapper.toEventShortDto(event, statsClient))
                        .toList())
                .build();
    }
}
