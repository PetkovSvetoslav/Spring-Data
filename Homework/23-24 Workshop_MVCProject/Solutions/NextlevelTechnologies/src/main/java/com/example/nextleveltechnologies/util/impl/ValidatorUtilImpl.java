package com.example.nextleveltechnologies.util.impl;

import com.example.nextleveltechnologies.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class ValidatorUtilImpl implements ValidatorUtil {

    private final Validator validator;

    @Autowired
    public ValidatorUtilImpl() {
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}
