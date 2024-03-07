package lt.techin.recipesharingplatform.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DisplayNameValidator.class)
public @interface NoOffensiveWords {
    String message() default "Display name contains offensive words";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
