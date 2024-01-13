package io.github.ninobomba.commons.exceptions.types.database;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class InvalidParameterProvidedException extends RuntimeException {

    public InvalidParameterProvidedException() {
    }

    public InvalidParameterProvidedException(String message) {
        super(message);
    }

    public InvalidParameterProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterProvidedException(Throwable cause) {
        super(cause);
    }

    public InvalidParameterProvidedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
