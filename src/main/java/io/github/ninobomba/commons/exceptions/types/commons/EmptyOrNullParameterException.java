package io.github.ninobomba.commons.exceptions.types.commons;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class EmptyOrNullParameterException extends RuntimeException {

    public EmptyOrNullParameterException() {
    }

    public EmptyOrNullParameterException(String message) {
        super(message);
    }

    public EmptyOrNullParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyOrNullParameterException(Throwable cause) {
        super(cause);
    }

    public EmptyOrNullParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
