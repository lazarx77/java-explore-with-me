package ru.practicum.user.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тестовый класс для проверки функциональности мапперов UserDtoMapper.
 * <p>
 * Этот класс содержит тесты, которые проверяют корректность преобразования объектов
 * User в различные DTO и обратно.
 */
class UserDtoMapperTest {

    private User user;
    private NewUserRequestDto newUserRequestDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        newUserRequestDto = new NewUserRequestDto();
        newUserRequestDto.setName("Jane Doe");
        newUserRequestDto.setEmail("jane@example.com");
    }

    @Test
    void mapToUser_whenNewUserRequestDtoIsProvided_thenUserIsReturned() {
        User mappedUser = UserDtoMapper.mapToUser(newUserRequestDto);

        assertNotNull(mappedUser);
        assertEquals(newUserRequestDto.getName(), mappedUser.getName());
        assertEquals(newUserRequestDto.getEmail(), mappedUser.getEmail());
    }

    @Test
    void mapToUserDto_whenUserIsProvided_thenUserDtoIsReturned() {
        UserDto userDto = UserDtoMapper.mapToUserDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void toUserShortDto_whenUserIsProvided_thenUserShortDtoIsReturned() {
        UserShortDto userShortDto = UserDtoMapper.toUserShortDto(user);

        assertNotNull(userShortDto);
        assertEquals(user.getId(), userShortDto.getId());
        assertEquals(user.getName(), userShortDto.getName());
    }
}