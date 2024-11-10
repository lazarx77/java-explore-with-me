package ru.practicum.category.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность категории, представляющая таблицу "categories" в базе данных.
 * <p>
 * Этот класс используется для хранения информации о категориях в приложении.
 * Он содержит уникальный идентификатор и название категории, которое должно быть
 * уникальным и не может быть пустым.
 */
@Entity
@Table(name = "categories")
@EqualsAndHashCode(of = "name")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 50)
    @NotBlank(message = "Название категории не может быть пустым")
    private String name;
}
