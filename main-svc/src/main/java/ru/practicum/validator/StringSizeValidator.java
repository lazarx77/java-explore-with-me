package ru.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.exception.StringSizeException;

/**
 * Валидатор для проверки длины строковых параметров, таких как название, описание, аннотация и название категории.
 */
@Slf4j
public class StringSizeValidator {

    /**
     * Проверяет, что длина названия (title) находится в пределах от 3 до 120 символов.
     *
     * @param string название для проверки
     * @throws StringSizeException если длина названия не соответствует требованиям
     */
    public static void validateTitle(String string) {
        log.info("Валидация длины названия (title)");
        if (string.length() < 3 || string.length() > 120) {
            log.error("Длина названия (title) должна быть от 3 до 120 символов. Фактически: {}", string.length());
            throw new StringSizeException("Длина названия (title) должна быть от 3 до 120 символов." +
                    " Фактически: " + string.length());
        }
    }

    /**
     * Проверяет, что длина описания (description) находится в пределах от 20 до 7000 символов.
     *
     * @param string описание для проверки
     * @throws StringSizeException если длина описания не соответствует требованиям
     */
    public static void validateDescription(String string) {
        log.info("Валидация длины описания (description)");
        if (string.length() < 20 || string.length() > 7000) {
            log.error("Длина описания (description) должна быть от 20 до 7000 символов. Фактически: {}", string.length());
            throw new StringSizeException("Длина описания (description) должна быть от 20 до 7000 символов. " +
                    "Фактически: " + string.length());
        }
    }

    /**
     * Проверяет, что длина аннотации (annotation) находится в пределах от 20 до 2000 символов.
     *
     * @param string аннотация для проверки
     * @throws StringSizeException если длина аннотации не соответствует требованиям
     */
    public static void validateAnnotation(String string) {
        log.info("Валидация длины аннотации (annotation)");
        if (string.length() < 20 || string.length() > 2000) {
            log.error("Длина аннотации (annotation) должна быть от 20 до 2000 символов. Фактически: {}", string.length());
            throw new StringSizeException("Длина аннотации (annotation) должна быть от 20 до 2000 символов." +
                    "Фактически: " + string.length());
        }
    }

    /**
     * Проверяет, что длина названия категории (categoryName) находится в пределах от 1 до 50 символов.
     *
     * @param name название категории для проверки
     * @throws StringSizeException если длина названия категории не соответствует требованиям
     */
    public static void validateCategoryName(String name) {
        log.info("Валидация длины названия категории (categoryName)");
        if (name.isEmpty() || name.length() > 50) {
            log.error("Длина названия категории (categoryName) должна быть от 1 до 50 символов. " +
                    "Фактически: {}", name.length());
            throw new StringSizeException("Длина названия категории (categoryName) должна быть от 1 до 50 символов." +
                    "Фактически: " + name.length());
        }
    }

}
