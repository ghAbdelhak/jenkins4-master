package com.example.livapp.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class QuantityReservedValidator implements ConstraintValidator<QuantityReservedConstraint, Object> {
    private String quantityFieldName;
    private String quantityReservedFieldName;

    @Override
    public void initialize(QuantityReservedConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        quantityFieldName = constraintAnnotation.quantity();
        quantityReservedFieldName = constraintAnnotation.reservedQuantity();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        final Field quantityField;
        try {
            quantityField = value.getClass().getDeclaredField(quantityFieldName);
            quantityField.setAccessible(true);
            final Field quantityReservedField = value.getClass().getDeclaredField(quantityReservedFieldName);
            quantityReservedField.setAccessible(true);
            final Integer quantity = Integer.parseInt(quantityField.get(value).toString());
            final Integer quantityReserved = Integer.parseInt(quantityReservedField.get(value).toString());
            return (quantityReserved <= quantity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }


    }
}

