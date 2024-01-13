package io.github.ninobomba.commons.exceptions.types.process;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class ValidationProcessException extends RuntimeException {

    public ValidationProcessException() {
    }

    public ValidationProcessException(String message) {
        super(message);
    }

    public ValidationProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationProcessException(Throwable cause) {
        super(cause);
    }

    public ValidationProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
