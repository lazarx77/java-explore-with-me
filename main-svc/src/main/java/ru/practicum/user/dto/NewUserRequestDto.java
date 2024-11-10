package ru.practicum.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для создания нового пользователя.
 * Содержит информацию о пользователе, включая имя и адрес электронной почты.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequestDto {

    @Size(min = 2, max = 250)
    @NotBlank(message = "Поле name не может быть пустым")
    private String name;
    @Size(min = 6, max = 254)
    @NotBlank(message = "Поле email не может быть пустым")
    @Email
    private String email;
}
