package ru.practicum.util;

import java.time.format.DateTimeFormatter;

/**
 * Утилитарный класс для работы с форматами даты и времени.
 */
public class DateTimeUtil {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd' 'HH:mm:ss";
}
