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
import org.springframework.web.context.annotation.RequestScope;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.EventService;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;
import ru.practicum.validator.DateTimeValidator;
import ru.practicum.validator.LocationValidator;
import ru.practicum.validator.StringSizeValidator;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Utils.DATE_TIME_FORMAT;

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

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody NewUserRequestDto dto) {
        log.info("Вызывается метод createUser в AdminController");
        return userService.createUser(dto);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(defaultValue = "10") @Positive int size,
                                  @RequestParam(required = false) Long[] ids) {
        log.info("Вызывается метод getAllUsers в AdminController");
        return userService.getUsers(from, size, ids);
    }

    @DeleteMapping("users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive(message = "id пользователя должен быть положительным") Long userId) {
        log.info("Вызывается метод deleteUser в AdminController");
        userService.deleteUser(userId);
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto updateUser(@RequestBody NewCategoryDto dto) {
        log.info("Вызывается метод updateUser в AdminController");
        return categoryService.createNewCategory(dto);
    }

    @PatchMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable @Positive(message = "id категории должен быть положительным")
                                      Long catId, @RequestBody CategoryDto dto) {
        log.info("Вызывается метод updateCategory в AdminController");
        return categoryService.updateCategory(catId, dto);
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Positive(message = "id категории должен быть положительным") Long catId) {
        log.info("Вызывается метод deleteCategory в AdminController");
        categoryService.deleteCategory(catId);
    }

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
            DateTimeValidator.ValidateDatetime(dto.getEventDate());
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

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.OK)
    public Compilation createCompilation(@RequestBody @Valid NewCompilationDto dto) {
        log.info("Вызывается метод createCompilation в AdminController");
        if (dto.getPinned() == null) {
            dto.setPinned(false);
        }
        return compilationService.createCompilation(dto);
    }
}
