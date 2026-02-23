package com.example.livapp.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = QuantityReservedValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface QuantityReservedConstraint {

    String message() default "{com.example.livapp.validator.QuantityReservedConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String quantity();

    String reservedQuantity();
}
