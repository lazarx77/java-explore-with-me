package ru.practicum.event.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.practicum.event.validator.AfterDurationValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AfterDurationValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterDuration {
    String message() default "Дата события должна быть через заданный временной промежуток после текущего времени";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value(); // Параметр для указания временного промежутка в формате "PT2H" (2 часа)
}

