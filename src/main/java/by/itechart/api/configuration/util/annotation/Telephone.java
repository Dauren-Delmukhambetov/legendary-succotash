package by.itechart.api.configuration.util.annotation;

import by.itechart.api.configuration.util.validation.TelephoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelephoneValidator.class)
@Documented
public @interface Telephone {

    String message() default "Invalid telephone pattern";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
