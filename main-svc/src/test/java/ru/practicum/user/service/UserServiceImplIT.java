package ru.practicum.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.exception.EmailDoubleException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Интеграционный тестовый класс для проверки функциональности сервиса UserServiceImpl.
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class UserServiceImplIT {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private NewUserRequestDto newUserRequestDto;

    @BeforeEach
    void setUp() {
        newUserRequestDto = new NewUserRequestDto("name", "email@mail.ru");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_whenUserIsValid_userIsSaved() {
        UserDto savedUserDto = userService.createUser(newUserRequestDto);
        assertThat(savedUserDto).isNotNull();
        assertThat(savedUserDto.getName()).isEqualTo("name");
        assertThat(savedUserDto.getEmail()).isEqualTo("email@mail.ru");
    }

    @Test
    void getUsers_whenUsersExist_usersAreReturned() {
        userService.createUser(newUserRequestDto);
        List<UserDto> users = userService.getUsers(0, 10, null);
        assertThat(users).hasSize(1);
    }

    @Test
    void deleteUser_whenUserExists_userIsDeleted() {
        UserDto addedUserDto = userService.createUser(newUserRequestDto);
        userService.deleteUser(addedUserDto.getId());

        List<UserDto> users = userService.getUsers(0, 10, null);
        assertThat(users).isEmpty();
    }

    @Test
    void deleteUser_whenUserNotFound_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> userService.deleteUser(999L));
    }

    @Test
    void createUser_whenEmailIsNotUnique_throwsEmailNotUniqueException() {
        userService.createUser(newUserRequestDto);
        NewUserRequestDto anotherUserRequestDto = new NewUserRequestDto("anotherName", "email@mail.ru");

        assertThrows(EmailDoubleException.class, () -> userService.createUser(anotherUserRequestDto));
    }
}