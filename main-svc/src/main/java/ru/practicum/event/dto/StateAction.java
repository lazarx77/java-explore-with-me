package ru.practicum.event.dto;

import java.util.Optional;
import java.util.stream.Stream;

public enum StateAction {
    PUBLISH_EVENT,
    REJECT_EVENT,
    CANCEL_REVIEW,
    SEND_TO_REVIEW;

    public static Optional<StateAction> from(String stringStateAction) {
        return Stream.of(values())
                .filter(state -> state.name().equalsIgnoreCase(stringStateAction))
                .findFirst();
    }
}
