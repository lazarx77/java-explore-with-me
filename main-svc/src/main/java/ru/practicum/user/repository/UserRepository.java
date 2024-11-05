package ru.practicum.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE id IN (:ids) LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findAllUsersByIds(@Param("ids") List<Long> ids, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM users LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findAllUsers(@Param("limit") int limit, @Param("offset") int offset);

}
