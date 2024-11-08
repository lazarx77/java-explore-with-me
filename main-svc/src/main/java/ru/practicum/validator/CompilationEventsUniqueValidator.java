package ru.practicum.validator;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CompilationEventsUniqueValidator {

    public static void validateEventsUnique(List<Long> ids) {
        log.info("Проверка на уникальность событий в подборке: {}", ids);
        if (ids.size() != ids.stream().distinct().count()) {
            throw new IllegalArgumentException("События в подборке должны быть уникальными");
        }
    }
}
