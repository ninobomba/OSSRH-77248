package io.github.ninobomba.commons.exceptions.types.commons;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class InvalidInputParameterException extends RuntimeException {

    public InvalidInputParameterException() {
    }

    public InvalidInputParameterException(String message) {
        super(message);
    }

    public InvalidInputParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputParameterException(Throwable cause) {
        super(cause);
    }

    public InvalidInputParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
