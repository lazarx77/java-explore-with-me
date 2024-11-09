package ru.practicum.request.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum Status {
    PENDING,
    CONFIRMED,
    REJECTED,
    CANCELED;

    public static Optional<Status> from(String stringStatus) {
        return Stream.of(values())
                .filter(state -> state.name().equalsIgnoreCase(stringStatus))
                .findFirst();
    }
}
