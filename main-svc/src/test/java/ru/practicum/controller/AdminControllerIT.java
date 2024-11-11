package ru.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.category.service.CategoryService;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.EmailDoubleException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Класс для тестирования контроллера администратора.
 */
@WebMvcTest(AdminController.class)
class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private EventService eventService;

    @MockBean
    private CompilationService compilationService;

    @SneakyThrows
    @Test
    void createUser_whenValidUser_thenReturnsCreatedUser() {
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto("UserName", "user@mail.ru");
        UserDto expectedUserDto = new UserDto(1L, "UserName", "user@mail.ru");

        when(userService.createUser(any(NewUserRequestDto.class))).thenReturn(expectedUserDto);

        mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("UserName"))
                .andExpect(jsonPath("$.email").value("user@mail.ru"));
    }

    @SneakyThrows
    @Test
    void createUser_whenEmailAlreadyExists_thenReturnsConflict() {
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto("UserName", "user@mail.ru");

        when(userService.createUser(any(NewUserRequestDto.class)))
                .thenThrow(new EmailDoubleException("Пользователь с таким email уже зарегистрирован"));

        mockMvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Пользователь с таким email уже зарегистрирован"))
                .andExpect(jsonPath("$.reason").value("Нарушено ограничение целостности."))
                .andExpect(jsonPath("$.status").value("CONFLICT"));
    }

    @SneakyThrows
    @Test
    void getUsers_whenInvoked_thenReturnsListOfUsers() {
        List<UserDto> expectedUsers = List.of(new UserDto(1L, "UserName", "user@mail.ru"));

        when(userService.getUsers(0, 10, null)).thenReturn(expectedUsers);

        mockMvc.perform(get("/admin/users")
                        .param("from", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("UserName"))
                .andExpect(jsonPath("$[0].email").value("user@mail.ru"));
    }

    @SneakyThrows
    @Test
    void deleteUser_whenUserExists_thenReturnsNoContent() {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/admin/users/{userId}", 1L))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

    @SneakyThrows
    @Test
    void deleteUser_whenUserNotFound_thenReturnsNotFound() {
        doThrow(new NotFoundException("Пользователь с таким id не найден")).when(userService).deleteUser(1L);

        mockMvc.perform(delete("/admin/users/{userId}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
