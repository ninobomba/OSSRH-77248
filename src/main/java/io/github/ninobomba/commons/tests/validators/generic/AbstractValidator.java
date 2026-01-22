package io.github.ninobomba.commons.tests.validators.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Abstract validator providing a fluent API for rule-based validation.
 * This class holds the value to be validated and executes checks immediately.
 *
 * @param <V> the type of the value to be validated
 * @param <T> the type of the validator for fluent API chaining
 */
public abstract class AbstractValidator<V, T extends AbstractValidator<V, T>> {

    protected final V value;
    protected final List<String> validationErrors = new ArrayList<>();

    protected AbstractValidator(V value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    protected T check(Predicate<V> predicate, String reason) {
        if (predicate != null && !predicate.test(this.value)) {
            validationErrors.add(reason);
        }
        return (T) this;
    }
    
    public boolean isValid() {
        return validationErrors.isEmpty();
    }

    public boolean hasErrors() {
        return !validationErrors.isEmpty();
    }

    public List<String> getValidationErrors() {
        return new ArrayList<>(validationErrors);
    }
}