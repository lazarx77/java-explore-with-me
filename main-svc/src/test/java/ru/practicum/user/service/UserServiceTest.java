package ru.practicum.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.exception.EmailDoubleException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки функциональности сервиса UserServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void createUser_whenEmailNotDouble_thenUserReturned() {
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto("name", "email@mail.ru");
        User expectedUser = new User(1L, "name", "email@mail.ru");
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        UserDto actualUserDto = userService.createUser(newUserRequestDto);

        assertEquals(expectedUser.getId(), actualUserDto.getId());
        assertEquals(expectedUser.getName(), actualUserDto.getName());
        assertEquals(expectedUser.getEmail(), actualUserDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_whenEmailDouble_thenEmailDoubleExceptionThrownAndUserNotSaved() {
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto("name", "email@mail.ru");
        User userInDb = new User(2L, "name2", "email@mail.ru");

        when(userRepository.findByEmail(newUserRequestDto.getEmail())).thenReturn(Optional.of(userInDb));

        EmailDoubleException exception = assertThrows(EmailDoubleException.class, () -> {
            userService.createUser(newUserRequestDto);
        });

        assertEquals("Пользователь с таким email уже зарегистрирован", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUsers_whenInvoked_thenListOfUsersReturned() {
        User expectedUser = new User(1L, "name", "email@mail.ru");
        when(userRepository.findAllUsers(anyInt(), anyInt())).thenReturn(List.of(expectedUser));

        List<UserDto> actualUsersList = userService.getUsers(0, 10, null);

        assertEquals(expectedUser.getId(), actualUsersList.get(0).getId());
        assertEquals(expectedUser.getName(), actualUsersList.get(0).getName());
        assertEquals(expectedUser.getEmail(), actualUsersList.get(0).getEmail());
    }

    @Test
    void deleteUser_whenUserExists_thenDeleteIsCalled() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_whenUserNotFound_thenNotFoundExceptionThrown() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));
    }
}