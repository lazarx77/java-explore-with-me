package ru.practicum.event.dto;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Перечисление действий состояния события.
 * Содержит возможные действия, которые могут быть выполнены над событием,
 * такие как публикация, отклонение, отмена рецензии и отправка на рецензию.
 */
public enum StateAction {
    PUBLISH_EVENT,
    REJECT_EVENT,
    CANCEL_REVIEW,
    SEND_TO_REVIEW;

    /**
     * Преобразует строковое представление действия состояния в соответствующий элемент перечисления.
     *
     * @param stringStateAction строковое представление действия состояния.
     * @return Optional, содержащий соответствующий элемент перечисления, если он найден.
     */
    public static Optional<StateAction> from(String stringStateAction) {
        return Stream.of(values())
                .filter(state -> state.name().equalsIgnoreCase(stringStateAction))
                .findFirst();
    }
}
