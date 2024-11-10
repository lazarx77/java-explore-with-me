package ru.practicum.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.user.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с пользователями.
 * Предоставляет методы для выполнения операций CRUD и дополнительных запросов.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по адресу электронной почты.
     *
     * @param email Адрес электронной почты пользователя.
     * @return Optional, содержащий найденного пользователя, если он существует.
     */
    Optional<User> findByEmail(String email);

    /**
     * Находит всех пользователей по списку идентификаторов с поддержкой пагинации.
     *
     * @param ids    Список идентификаторов пользователей.
     * @param limit  Максимальное количество пользователей для возврата.
     * @param offset Смещение для пагинации.
     * @return Список пользователей, соответствующих указанным идентификаторам.
     */
    @Query(value = "SELECT * FROM users WHERE id IN (:ids) LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findAllUsersByIds(@Param("ids") List<Long> ids, @Param("limit") int limit, @Param("offset") int offset);

    /**
     * Находит всех пользователей с поддержкой пагинации.
     *
     * @param limit  Максимальное количество пользователей для возврата.
     * @param offset Смещение для пагинации.
     * @return Список всех пользователей.
     */
    @Query(value = "SELECT * FROM users LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findAllUsers(@Param("limit") int limit, @Param("offset") int offset);

}
