package ru.practicum.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exception.EmailDoubleException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserDtoMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для управления пользователями.
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto createUser(NewUserRequestDto dto) {
        log.info("Вызывается метод createUser в UserServiceImpl");
        log.info("Проверка на повтор email {}", dto.getEmail());
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailDoubleException("Пользователь с таким email уже зарегистрирован");
        }
        User user = UserDtoMapper.mapToUser(dto);
        return UserDtoMapper.mapToUserDto(userRepository.save(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> getUsers(int from, int size, Long[] ids) {
        log.info("Вызывается метод getUsers в UserServiceImpl");

        List<Long> idList = (ids != null) ? Arrays.asList(ids) : null;

        List<User> userList = (ids == null)
                ? userRepository.findAllUsers(size, from)
                : userRepository.findAllUsersByIds(idList, size, from);
        return userList.stream().map(UserDtoMapper::mapToUserDto).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(Long userId) {
        log.info("Вызывается метод deleteUser в UserServiceImpl");
        log.info("Проверка на существование пользователя с id {}", userId);
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
        log.info("Удаляем пользователя с id {}", userId);
        userRepository.deleteById(userId);
    }
}
