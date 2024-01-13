package io.github.ninobomba.commons.exceptions.types.api;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class UnexpectedApiClientResponseException extends RuntimeException {

    public UnexpectedApiClientResponseException() {
    }

    public UnexpectedApiClientResponseException(String message) {
        super(message);
    }

    public UnexpectedApiClientResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedApiClientResponseException(Throwable cause) {
        super(cause);
    }

    public UnexpectedApiClientResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
