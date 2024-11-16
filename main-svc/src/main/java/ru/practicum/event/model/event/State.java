package ru.practicum.event.model.event;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Перечисление, представляющее возможные состояния события.
 * Состояния включают:
 */
public enum State {

    PENDING,
    PUBLISHED,
    CANCELED;

    /**
     * Метод для получения состояния события по его строковому представлению.
     *
     * @param stringState строковое представление состояния события.
     * @return Optional<State> - возвращает найденное состояние, если оно существует, иначе - пустой Optional.
     */
    public static Optional<State> from(String stringState) {
        return Stream.of(values())
                .filter(state -> state.name().equalsIgnoreCase(stringState))
                .findFirst();
    }
}
