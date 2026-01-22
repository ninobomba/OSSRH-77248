package io.github.ninobomba.commons.tests.validators.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public final class ValidationUtil {

    private static final Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    public static <T> Set<ConstraintViolation<T>> validate(T t) {
        return validator.validate(t);
    }

}
