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
import ru.practicum.event.dto.event.EventFullDto;
import ru.practicum.event.dto.event.EventShortDto;
import ru.practicum.event.service.EventService;
import ru.practicum.stats_client.client.StatsClient;
import ru.practicum.validator.ParamsValidator;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Utils.APP_NAME;
import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

/**
 * Контроллер для обработки публичных запросов к ресурсам приложения.
 * Обеспечивает доступ к категориям, событиям и подборкам для пользователей.
 */
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

    /**
     * Получает список категорий с пагинацией.
     *
     * @param from индекс для пагинации
     * @param size количество категорий для выборки
     * @return список категорий
     */
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        return categoryService.getCategories(from, size);
    }

    /**
     * Получает категорию по идентификатору.
     *
     * @param catId идентификатор категории
     * @return категория
     */
    @GetMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable @Positive(message = "id категории должен быть положительным")
                                   Long catId) {
        return categoryService.getCategory(catId);
    }

    /**
     * Получает событие по идентификатору.
     * Также регистрирует статистику доступа к событию.
     *
     * @param eventId идентификатор события
     * @param request объект HTTP-запроса
     * @return полное описание события
     */
    @GetMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@PathVariable @Positive(message = "id события должен быть положительным")
                                 Long eventId, HttpServletRequest request) {
        statsClient.addStats(APP_NAME, request);
        return eventService.getEventPublic(eventId);
    }

    /**
     * Получает список событий с возможностью фильтрации и пагинации.
     *
     * @param text          текст для поиска
     * @param categories    массив идентификаторов категорий
     * @param paid          фильтр по платным событиям
     * @param rangeStart    начало диапазона дат
     * @param rangeEnd      конец диапазона дат
     * @param onlyAvailable фильтр по доступным событиям
     * @param sort          порядок сортировки
     * @param from          индекс для пагинации
     * @param size          количество событий для выборки
     * @param request       объект HTTP-запроса
     * @return список событий
     */
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

    /**
     * Получает список подборок событий с пагинацией.
     *
     * @param pinned фильтр по закрепленным подборкам
     * @param from   индекс для пагинации
     * @param size   количество подборок для выборки
     * @return список подборок
     */
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
