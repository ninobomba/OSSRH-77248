package io.github.ninobomba.commons.exceptions.types.system;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class SystemIllegalAccessException extends RuntimeException {

    public SystemIllegalAccessException() {
    }

    public SystemIllegalAccessException(String message) {
        super(message);
    }

    public SystemIllegalAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemIllegalAccessException(Throwable cause) {
        super(cause);
    }

    public SystemIllegalAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
