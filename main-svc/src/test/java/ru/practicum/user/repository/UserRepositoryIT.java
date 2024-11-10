package ru.practicum.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.user.model.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Интеграционный тестовый класс для проверки функциональности репозитория UserRepository.
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class UserRepositoryIT {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user = new User(1L, "name", "email@mail.ru");
    }

    @Test
    void save_whenUserIsValid_userIsSaved() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("name");
        assertThat(savedUser.getEmail()).isEqualTo("email@mail.ru");
    }

    @Test
    void findById_whenUserExists_userIsReturned() {
        User addedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(addedUser.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    void findById_whenUserNotFound_optionalIsEmpty() {
        Optional<User> foundUser = userRepository.findById(999L);
        assertThat(foundUser).isEmpty();
    }

    @Test
    void findAll_whenUsersExist_usersAreReturned() {
        User user1 = new User(1L, "name1", "email1@mail.ru");
        User user2 = new User(2L, "name2", "email2@mail.ru");
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).contains(user1, user2);
    }

    @Test
    void deleteById_whenUserExists_userIsDeleted() {
        User addedUser = userRepository.save(user);
        userRepository.deleteById(addedUser.getId());
        Optional<User> foundUser = userRepository.findById(addedUser.getId());
        assertThat(foundUser).isEmpty();
    }

    @Test
    void deleteById_whenUserNotFound_nothingIsDeleted() {
        userRepository.deleteById(999L);
        List<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    void findByEmail_whenUserExists_userIsReturned() {
        User addedUser = userRepository.save(user);
        User foundUser = userRepository.findByEmail(addedUser.getEmail()).orElseThrow();
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void findByEmail_whenUserNotFound_throwsNotFoundException() {
        assertThrows(NoSuchElementException.class, () -> userRepository.findByEmail("non-existent-email@mail.ru").orElseThrow());
    }
}
