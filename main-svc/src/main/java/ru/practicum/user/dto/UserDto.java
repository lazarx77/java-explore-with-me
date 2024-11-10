package ru.practicum.user.dto;

import lombok.*;

/**
 * DTO для представления пользователя.
 * Содержит информацию о пользователе, включая его идентификатор, имя и адрес электронной почты.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String email;
}
