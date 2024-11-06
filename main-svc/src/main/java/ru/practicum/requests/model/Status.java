package ru.practicum.requests.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum Status {
    PENDING,
    REJECTED,
    CONFIRMED;

    public static Optional<Status> from(String stringStatus) {
        return Stream.of(values())
                .filter(state -> state.name().equalsIgnoreCase(stringStatus))
                .findFirst();
    }
}
