package ru.practicum.util;

import java.time.format.DateTimeFormatter;

/**
 * Утилитарный класс для работы с константами и форматом времени.
 * Содержит предопределенные форматы для форматирования и парсинга дат и времени,
 * а также константы, используемые в приложении.
 */
public class Utils {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd' 'HH:mm:ss";
    public static final String APP_NAME = "ewm-main-service";
}
