package ru.practicum.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для проверки функциональности контроллера AdminController.
 */
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void createUser_whenInvoked_thenUserDtoReturned() {
        NewUserRequestDto dto = new NewUserRequestDto("UserName", "user@mail.ru");
        UserDto expectedUserDto = new UserDto(1L, "UserName", "user@mail.ru");
        when(userService.createUser(dto)).thenReturn(expectedUserDto);

        UserDto actualUserDto = adminController.createUser(dto);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void getUsers_whenInvoked_thenUserDtosCollectionReturned() {
        List<UserDto> expectedUserDtos = List.of(new UserDto(1L, "UserName", "user@mail.ru"));
        when(userService.getUsers(0, 10, null)).thenReturn(expectedUserDtos);

        List<UserDto> actualUserDtos = adminController.getUsers(0, 10, null);

        assertEquals(expectedUserDtos, actualUserDtos);
    }

    @Test
    void deleteUser_whenInvoked_thenNoExceptionThrown() {
        adminController.deleteUser(1L);
    }
}