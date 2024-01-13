package io.github.ninobomba.commons.exceptions.types.process;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
public final class BuildProcessException extends RuntimeException {

    public BuildProcessException() {
    }

    public BuildProcessException(String message) {
        super(message);
    }

    public BuildProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildProcessException(Throwable cause) {
        super(cause);
    }

    public BuildProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
