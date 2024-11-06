package ru.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.exception.StringSizeException;

@Slf4j
public class StringSizeValidator {

    public static void validateTitle(String string) {
        log.info("Валидация длины названия (title)");
        if (string.length() < 3 || string.length() > 120) {
            log.error("Длина названия (title) должна быть от 3 до 120 символов. Фактически: {}", string.length());
            throw new StringSizeException("Длина названия (title) должна быть от 3 до 120 символов." +
                    " Фактически: " + string.length());
        }
    }

    public static void validateDescription(String string) {
        log.info("Валидация длины описания (description)");
        if (string.length() < 20 || string.length() > 7000) {
            log.error("Длина описания (description) должна быть от 20 до 7000 символов. Фактически: {}", string.length());
            throw new StringSizeException("Длина описания (description) должна быть от 20 до 7000 символов. " +
                    "Фактически: " + string.length());
        }
    }

    public static void validateAnnotation(String string) {
        log.info("Валидация длины аннотации (annotation)");
        if (string.length() < 20 || string.length() > 2000) {
            log.error("Длина аннотации (annotation) должна быть от 20 до 2000 символов. Фактически: {}", string.length());
            throw new StringSizeException("Длина аннотации (annotation) должна быть от 20 до 2000 символов." +
                    "Фактически: " + string.length());
        }
    }
}
