package ru.practicum.user.mapper;

import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

/**
 * Mapper для преобразования объектов между DTO и моделью пользователя.
 */
public class UserDtoMapper {

    /**
     * Преобразует NewUserRequestDto в объект User.
     *
     * @param dto DTO с данными нового пользователя.
     * @return Объект User, созданный на основе данных из DTO.
     */
    public static User mapToUser(NewUserRequestDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    /**
     * Преобразует объект User в UserDto.
     *
     * @param user Объект пользователя, который необходимо преобразовать.
     * @return DTO пользователя, содержащий идентификатор, имя и email.
     */
    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    /**
     * Преобразует объект User в UserShortDto.
     *
     * @param user Объект пользователя, который необходимо преобразовать.
     * @return DTO пользователя, содержащий только идентификатор и имя.
     */
    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
