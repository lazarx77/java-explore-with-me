package ru.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.exception.EventDateTimeException;

import java.time.LocalDateTime;

@Slf4j
public class DateTimeValidator {

    public static void ValidateDatetime(LocalDateTime dateTime) {
        log.info("Валидация даты и времени начала события");
        LocalDateTime expectedEventDate = LocalDateTime.now().plusHours(2);
        if (dateTime.isBefore(expectedEventDate)) {
            log.info("Дата/время начала события: {}, а должны быть: {}", dateTime, expectedEventDate);
            throw new EventDateTimeException("Дата/время начала события не могут быть раньше, чем через 2 часа, а " +
                    "именно: " + expectedEventDate);
        }
    }

    public static void ValidateDatetimeOnUpdate(LocalDateTime dateTime) {
        log.info("Валидация даты и времени начала события при обновлении администратором");
        LocalDateTime expectedEventDate = LocalDateTime.now().plusHours(1);
        if (dateTime.isBefore(expectedEventDate)) {
            log.info("Дата/время начала события: {}, а должны быть: {}", dateTime, expectedEventDate);
            throw new EventDateTimeException("Дата/время начала редактируемого события не могут быть раньше, чем " +
                    "через 1 часа, а именно: " + expectedEventDate);
        }
    }
}
