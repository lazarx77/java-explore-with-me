package ru.practicum.request.model;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Перечисление статусов запроса на участие в событии.
 */
public enum Status {
    PENDING,
    CONFIRMED,
    REJECTED,
    CANCELED;

    /**
     * Преобразует строковое представление статуса в соответствующий объект Status.
     *
     * @param stringStatus Строковое представление статуса.
     * @return Optional, содержащий статус, если он найден, или пустой Optional, если статус не найден.
     */
    public static Optional<Status> from(String stringStatus) {
        return Stream.of(values())
                .filter(state -> state.name().equalsIgnoreCase(stringStatus))
                .findFirst();
    }
}
