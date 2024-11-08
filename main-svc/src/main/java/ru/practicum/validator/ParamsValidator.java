package ru.practicum.validator;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ParamsValidator {

    public static void validateGetEventsPublicParams(Long[] categories,
                                                     LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd,
                                                     String sortToUpperCase) {
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

//        if (onlyAvailable == null) {
//            throw new IllegalArgumentException("Некорректный параметр onlyAvailable. Должен быть true или false.");
//        }
    }
}
