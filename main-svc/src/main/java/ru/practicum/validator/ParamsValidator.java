package ru.practicum.validator;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Валидатор для проверки параметров, передаваемых в метод получения событий в пубичном контроллере.
 * Содержит методы для валидации параметров, таких как категории, временные рамки и параметры сортировки.
 */
@Slf4j
public class ParamsValidator {

    /**
     * Проверяет корректность параметров для метода получения публичных событий.
     *
     * @param categories      массив идентификаторов категорий для фильтрации событий
     * @param rangeStart      начальная дата и время для фильтрации событий
     * @param rangeEnd        конечная дата и время для фильтрации событий
     * @param sortToUpperCase параметр сортировки, который должен быть либо "EVENT_DATE", либо "VIEWS"
     * @throws IllegalArgumentException если параметры не соответствуют требованиям
     */
    public static void validateGetEventsPublicParams(Long[] categories,
                                                     LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd,
                                                     String sortToUpperCase) {
        log.info("Валидация параметров для метода getEventsPublic");
        if (sortToUpperCase != null &&
                !Arrays.asList("EVENT_DATE", "VIEWS").contains(sortToUpperCase)) {
            throw new IllegalArgumentException("Некорректный параметр сортировки. Допустимые значения: EVENT_DATE, " +
                    "VIEWS. Переданное значение: " + sortToUpperCase);
        }

        if (categories != null && Arrays.stream(categories).anyMatch(category -> category < 0)) {
            throw new IllegalArgumentException("Некорректный параметр категории. В массиве есть отрицательные " +
                    "значения.");
        }

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new IllegalArgumentException("Некорректный параметр rangeStart. Переданное значение: " + rangeStart);
        }
    }
}
