package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для краткого представления пользователя.
 * Содержит минимально необходимую информацию о пользователе, включая его идентификатор и имя.
 */
@Getter
@Setter
@Builder
public class UserShortDto {

    private Long id;
    private String name;
}
