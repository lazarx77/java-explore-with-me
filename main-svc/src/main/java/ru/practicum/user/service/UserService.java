package ru.practicum.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;

import java.util.List;

/**
 * Интерфейс сервиса для управления пользователями.
 * Предоставляет методы для создания, получения и удаления пользователей.
 */
@Transactional(readOnly = true)
public interface UserService {

    /**
     * Создает нового пользователя на основе предоставленного DTO.
     *
     * @param dto объект, содержащий данные нового пользователя
     * @return DTO созданного пользователя
     */
    @Transactional
    UserDto createUser(NewUserRequestDto dto);

    /**
     * Получает список пользователей с возможностью пагинации.
     *
     * @param from начальный индекс для выборки
     * @param size количество пользователей для выборки
     * @param ids  массив идентификаторов пользователей для фильтрации
     * @return список DTO пользователей
     */
    List<UserDto> getUsers(int from, int size, Long[] ids);

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя, которого необходимо удалить
     */
    @Transactional
    void deleteUser(Long userId);
}
