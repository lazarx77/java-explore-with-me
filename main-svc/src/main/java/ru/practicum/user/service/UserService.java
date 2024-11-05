package ru.practicum.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.List;

@Transactional(readOnly = true)
public interface UserService {

    @Transactional
    UserDto createUser(NewUserRequestDto dto);

    List<UserDto> getUsers(int from, int size, Long[] ids);

    @Transactional
    void deleteUser(Long userId);
}
