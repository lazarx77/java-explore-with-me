package ru.practicum.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
@AllArgsConstructor
@Slf4j
@Validated
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;

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

}
