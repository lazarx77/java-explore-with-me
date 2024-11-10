package ru.practicum.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Модель пользователя.
 * Содержит информацию о пользователе, включая его идентификатор, имя и адрес электронной почты.
 */
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "email")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 2, max = 250)
    private String name;
    @Column(nullable = false, unique = true)
    @Size(min = 6, max = 254)
    @Email
    private String email;
}
