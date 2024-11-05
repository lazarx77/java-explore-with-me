package ru.practicum.event.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.event.annotations.AfterDuration;

import java.time.Duration;
import java.time.LocalDateTime;

public class AfterDurationValidator implements ConstraintValidator<AfterDuration, LocalDateTime> {

    private String duration;

    @Override
    public void initialize(AfterDuration constraintAnnotation) {
        this.duration = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
//        if (eventDate == null) {
//            return true; // или false, в зависимости от вашей логики
//        }

        // Парсинг временного промежутка
        Duration durationToAdd;
        try {
            durationToAdd = Duration.parse(duration); // Ожидаем формат "PT2H" или "PT30M"
        } catch (Exception e) {
            return false; // Неверный формат
        }

        return eventDate.isAfter(LocalDateTime.now().plus(durationToAdd));
    }
}


