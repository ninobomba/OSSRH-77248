package io.github.ninobomba.commons.exceptions.types.commons;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class BlankValueException extends RuntimeException {

    public BlankValueException() {
    }

    public BlankValueException(String message) {
        super(message);
    }

    public BlankValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlankValueException(Throwable cause) {
        super(cause);
    }

    public BlankValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
