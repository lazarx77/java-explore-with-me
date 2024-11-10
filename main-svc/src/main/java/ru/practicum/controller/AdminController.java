package ru.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.EventService;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;
import ru.practicum.validator.CompilationEventsUniqueValidator;
import ru.practicum.validator.DateTimeValidator;
import ru.practicum.validator.LocationValidator;
import ru.practicum.validator.StringSizeValidator;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

/**
 * Контроллер для административных операций в приложении.
 * Обрабатывает запросы на создание, обновление и удаление пользователей, категорий, событий и подборок.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
@AllArgsConstructor
@Slf4j
@Validated
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;

    /**
     * Создает нового пользователя.
     *
     * @param dto данные нового пользователя
     * @return созданный пользователь
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid NewUserRequestDto dto) {
        log.info("Вызывается метод createUser в AdminController");
        return userService.createUser(dto);
    }

    /**
     * Получает список пользователей с пагинацией.
     *
     * @param from индекс для пагинации
     * @param size количество пользователей для выборки
     * @param ids  массив идентификаторов пользователей (необязательно)
     * @return список пользователей
     */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(defaultValue = "10") @Positive int size,
                                  @RequestParam(required = false) Long[] ids) {
        log.info("Вызывается метод getAllUsers в AdminController");
        return userService.getUsers(from, size, ids);
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param userId идентификатор пользователя
     */
    @DeleteMapping("users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive(message = "id пользователя должен быть положительным") Long userId) {
        log.info("Вызывается метод deleteUser в AdminController");
        userService.deleteUser(userId);
    }

    /**
     * Создает новую категорию.
     *
     * @param dto данные новой категории
     * @return созданная категория
     */
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto dto) {
        log.info("Вызывается метод updateUser в AdminController");
        return categoryService.createNewCategory(dto);
    }

    /**
     * Обновляет существующую категорию.
     *
     * @param catId идентификатор категории
     * @param dto   данные для обновления категории
     * @return обновленная категория
     */
    @PatchMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable @Positive(message = "id категории должен быть положительным")
                                      Long catId, @RequestBody CategoryDto dto) {
        log.info("Вызывается метод updateCategory в AdminController");
        if (dto.getName() != null) {
            StringSizeValidator.validateCategoryName(dto.getName());
        }
        return categoryService.updateCategory(catId, dto);
    }

    /**
     * Удаляет категорию по идентификатору.
     *
     * @param catId идентификатор категории
     */
    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Positive(message = "id категории должен быть положительным") Long catId) {
        log.info("Вызывается метод deleteCategory в AdminController");
        categoryService.deleteCategory(catId);
    }

    /**
     * Обновляет событие администратором.
     *
     * @param eventId идентификатор события
     * @param dto     данные для обновления события
     * @return обновленное событие
     */
    @PatchMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventByAdmin(@PathVariable
                                           @Positive(message = "id события должен быть положительным") Long eventId,
                                           @RequestBody UpdateEventAdminRequest dto) {
        log.info("Вызывается метод updateEvent в AdminController");
        if (dto.getDescription() != null) {
            StringSizeValidator.validateDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            DateTimeValidator.validateDateTime(dto.getEventDate());
        }
        if (dto.getAnnotation() != null) {
            StringSizeValidator.validateAnnotation(dto.getAnnotation());
        }
        if (dto.getTitle() != null) {
            StringSizeValidator.validateTitle(dto.getTitle());
        }
        if (dto.getLocation() != null) {
            LocationValidator.validateLocation(dto.getLocation());
        }
        return eventService.updateEventByAdmin(eventId, dto);
    }

    /**
     * Получает список событий с возможностью фильтрации и пагинации.
     *
     * @param users      массив идентификаторов пользователей (необязательно)
     * @param states     массив состояний событий (необязательно)
     * @param categories массив идентификаторов категорий (необязательно)
     * @param rangeStart начало диапазона дат (необязательно)
     * @param rangeEnd   конец диапазона дат (необязательно)
     * @param from       индекс для пагинации
     * @param size       количество событий
     * @return список событий
     */
    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEventsByAdmin(@RequestParam(required = false) Long[] users,
                                               @RequestParam(required = false) String[] states,
                                               @RequestParam(required = false) Long[] categories,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Вызывается метод getEventsByAdmin в AdminController");
        return eventService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Создает новую подборку событий.
     *
     * @param dto данные для новой подборки
     * @return созданная подборка
     */
    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDtoResponse createCompilation(@RequestBody @Valid NewCompilationDto dto) {
        log.info("Вызывается метод createCompilation в AdminController");
        if (dto.getEvents() != null) {
            CompilationEventsUniqueValidator.validateEventsUnique(dto.getEvents());
        }
        if (dto.getPinned() == null) {
            dto.setPinned(false);
        }
        return compilationService.createCompilation(dto);
    }

    /**
     * Обновляет существующую подборку событий.
     *
     * @param compId идентификатор подборки
     * @param dto    данные для обновления подборки
     * @return обновленная подборка
     */
    @PatchMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDtoResponse updateCompilation(@PathVariable
                                                    @Positive(message = "id подборки должен быть положительным")
                                                    Long compId, @RequestBody @Valid UpdateCompilationRequestDto dto) {
        log.info("Вызывается метод updateCompilation в AdminController");
        if (dto.getEvents() != null) {
            CompilationEventsUniqueValidator.validateEventsUnique(dto.getEvents());
        }

        return compilationService.updateCompilation(compId, dto);
    }

    /**
     * Удаляет подборку по идентификатору.
     *
     * @param compId идентификатор подборки
     */
    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable
                                  @Positive(message = "id подборки должен быть положительным")
                                  Long compId) {
        log.info("Вызывается метод deleteCompilation в AdminController");
        compilationService.deleteCompilation(compId);
    }
}
