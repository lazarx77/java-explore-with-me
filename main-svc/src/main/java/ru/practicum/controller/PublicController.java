package ru.practicum.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;
import ru.practicum.stats_client.client.StatsClient;
import ru.practicum.validator.ParamsValidator;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Utils.APP_NAME;
import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping
@Validated
public class PublicController {

    private final CategoryService categoryService;
    private final EventService eventService;
    private final StatsClient statsClient;
    private final CompilationService compilationService;

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable @Positive(message = "id категории должен быть положительным")
                                   Long catId) {
        return categoryService.getCategory(catId);
    }

    @GetMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@PathVariable @Positive(message = "id события должен быть положительным")
                                 Long eventId, HttpServletRequest request) {
        statsClient.addStats(APP_NAME, request);
        return eventService.getEventPublic(eventId);
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsPublic(@RequestParam(required = false) String text,
                                               @RequestParam(required = false) Long[] categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                               @RequestParam(required = false) Boolean onlyAvailable,
                                               @RequestParam(required = false) String sort,
                                               @RequestParam(required = false, defaultValue = "0")
                                               @PositiveOrZero int from,
                                               @RequestParam(required = false, defaultValue = "10")
                                               @Positive int size,
                                               HttpServletRequest request) {

        String sortToUpperCase = (sort != null) ? sort.toUpperCase() : null;

        log.info("Запуск валидации параметров запроса в getEventsPublic");
        ParamsValidator.validateGetEventsPublicParams(categories, rangeStart, rangeEnd, sortToUpperCase);

//        if (sortToUpperCase != null) {
//            if (!sortToUpperCase.equals("EVENT_DATE") && !sortToUpperCase.equals("VIEWS")) {
//                throw new IllegalArgumentException("Некорректный параметр сортировки. Допустимые значения: EVENT_DATE," +
//                        "VIEWS. Переданное значение: " + sortToUpperCase);
//            }
//        }

        statsClient.addStats(APP_NAME, request);

        return eventService.getEventsPublic(text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sortToUpperCase,
                from,
                size);

    }

    @GetMapping("/compilations")
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDtoResponse> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                        @RequestParam(required = false, defaultValue = "0")
                                                        @PositiveOrZero int from,
                                                        @RequestParam(required = false, defaultValue = "10")
                                                        @Positive int size) {
        return compilationService.getCompilationsPublic(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDtoResponse getCompilation(@PathVariable
                                                 @Positive(message = "id события должен быть положительным")
                                                 Long compId) {
        return compilationService.getCompilationPublic(compId);
    }


}
