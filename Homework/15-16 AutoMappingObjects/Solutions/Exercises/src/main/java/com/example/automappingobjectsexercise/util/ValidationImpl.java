package com.example.automappingobjectsexercise.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationImpl implements Validation {

    private final Validator validator;

    @Autowired
    public ValidationImpl() {
        this.validator = javax.validation.Validation
                .buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violation(E entity) {
        return this.validator.validate(entity);
    }

    @Override
    public <E> void validateEntity(E entity) {
        Set<ConstraintViolation<E>> violations = this.violation(entity);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(System.lineSeparator()))
            );
        }
    }
}
