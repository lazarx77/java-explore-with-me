package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@Builder
public class CompilationDtoResponse {

    private List<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
