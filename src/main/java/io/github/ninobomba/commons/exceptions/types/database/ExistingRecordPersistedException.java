package io.github.ninobomba.commons.exceptions.types.database;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class ExistingRecordPersistedException extends RuntimeException {

    public ExistingRecordPersistedException() {
    }

    public ExistingRecordPersistedException(String message) {
        super(message);
    }

    public ExistingRecordPersistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingRecordPersistedException(Throwable cause) {
        super(cause);
    }

    public ExistingRecordPersistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
