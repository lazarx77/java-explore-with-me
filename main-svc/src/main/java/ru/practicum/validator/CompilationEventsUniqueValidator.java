package ru.practicum.validator;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Валидатор для проверки уникальности событий в подборке.
 * Содержит методы для валидации списка идентификаторов событий.
 */
@Slf4j
public class CompilationEventsUniqueValidator {

    /**
     * Проверяет, что все события в подборке имеют уникальные идентификаторы.
     *
     * @param ids список идентификаторов событий для проверки
     * @throws IllegalArgumentException если в списке есть дубликаты
     */
    public static void validateEventsUnique(List<Long> ids) {
        log.info("Проверка на уникальность событий в подборке: {}", ids);
        if (ids.size() != ids.stream().distinct().count()) {
            throw new IllegalArgumentException("События в подборке должны быть уникальными");
        }
    }
}
