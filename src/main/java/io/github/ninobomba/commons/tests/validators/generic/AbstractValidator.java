package io.github.ninobomba.commons.tests.validators.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Abstract validator providing a fluent API for rule-based validation.
 * Note: This class is NOT thread-safe and should be instantiated per validation cycle.
 */
public abstract class AbstractValidator<T extends AbstractValidator<T>> {

    private record Rule(Predicate<Object> predicate, String reason) {
    }

    private final List<Rule> rules = new ArrayList<>();
    protected final List<String> validationErrors = new ArrayList<>();

    @SuppressWarnings("unchecked")
    protected <E> T rule(Predicate<E> predicate, String reason) {
        this.rules.add(new Rule((Predicate<Object>) predicate, reason));
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    protected T validate(Object value) {
        for (Rule rule : rules) {
            // Removed break to allow collecting multiple errors for the same field
            processRule(value, rule.predicate(), rule.reason());
        }
        this.rules.clear();
        return (T) this;
    }

    private boolean processRule(Object value, Predicate<Object> predicate, String reason) {
        try {
            if (predicate != null && !predicate.test(value)) {
                validationErrors.add(reason);
                return false;
            }
        } catch (ClassCastException e) {
            validationErrors.add("Incompatible type for validation");
            return false;
        }
        return true;
    }

    public void throwOnErrors(String exceptionPrefix) {
        if (!validationErrors.isEmpty()) {
            String combinedErrors = String.join(", ", validationErrors);
            validationErrors.clear();
            throw new IllegalArgumentException(exceptionPrefix + ": [" + combinedErrors + "]");
        }
    }

    public boolean isValid() {
        return validationErrors.isEmpty();
    }

    protected List<String> getValidationErrors() {
        return new ArrayList<>(validationErrors);
    }
}
