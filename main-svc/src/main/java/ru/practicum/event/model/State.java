package ru.practicum.event.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum State {

    PENDING,
    PUBLISHED,
    CANCELED;

    public static Optional<State> from(String stringState) {
        return Stream.of(values())
                .filter(state -> state.name().equalsIgnoreCase(stringState))
                .findFirst();
    }
}
