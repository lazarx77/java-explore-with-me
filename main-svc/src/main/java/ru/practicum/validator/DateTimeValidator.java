package ru.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.exception.EventDateTimeException;

import java.time.LocalDateTime;

/**
 * Валидатор для проверки корректности даты и времени событий.
 * Содержит методы для валидации даты и времени начала события как при создании, так и при обновлении.
 */
@Slf4j
public class DateTimeValidator {

    /**
     * Проверяет, что дата и время начала события не находятся раньше, чем через 2 часа от текущего времени.
     *
     * @param dateTime дата и время начала события для проверки
     * @throws EventDateTimeException если дата/время начала события раньше, чем через 2 часа
     */
    public static void validateDateTime(LocalDateTime dateTime) {
        log.info("Валидация даты и времени начала события");
        LocalDateTime expectedEventDate = LocalDateTime.now().plusHours(2);
        if (dateTime.isBefore(expectedEventDate)) {
            log.info("Дата/время начала события: {}, а должны быть: {}", dateTime, expectedEventDate);
            throw new EventDateTimeException("Дата/время начала события не могут быть раньше, чем через 2 часа, а " +
                    "именно: " + expectedEventDate);
        }
    }

    /**
     * Проверяет, что дата и время начала редактируемого события не находятся раньше, чем через 1 час от текущего времени.
     *
     * @param dateTime дата и время начала редактируемого события для проверки
     * @throws EventDateTimeException если дата/время начала редактируемого события раньше, чем через 1 час
     */
    public static void validateDateTimeOnUpdate(LocalDateTime dateTime) {
        log.info("Валидация даты и времени начала события при обновлении администратором");
        LocalDateTime expectedEventDate = LocalDateTime.now().plusHours(1);
        if (dateTime.isBefore(expectedEventDate)) {
            log.info("Дата/время начала события: {}, а должны быть: {}", dateTime, expectedEventDate);
            throw new EventDateTimeException("Дата/время начала редактируемого события не могут быть раньше, чем " +
                    "через 1 часа, а именно: " + expectedEventDate);
        }
    }
}
