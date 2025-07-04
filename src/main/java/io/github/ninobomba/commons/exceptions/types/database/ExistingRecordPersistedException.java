package io.github.ninobomba.commons.exceptions.types.database;

import lombok.EqualsAndHashCode;

/**
 * Represents an exception that is thrown when attempting to persist an existing record.
 * This exception extends the RuntimeException class and can contain a message and a cause.
 * The purpose of this exception is to inform the caller that the record being persisted already exists.
 *
 * @since 1.0
 */
@EqualsAndHashCode ( callSuper = false )
public final class ExistingRecordPersistedException extends RuntimeException {
	
	/**
	 * Constructs a new ExistingRecordPersistedException with no detail message.
	 */
	public ExistingRecordPersistedException ( ) {
	}
	
	/**
	 * Constructs a new ExistingRecordPersistedException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage method).
	 */
	public ExistingRecordPersistedException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new ExistingRecordPersistedException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ExistingRecordPersistedException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Constructs a new ExistingRecordPersistedException with the specified cause.
	 *
	 * @param cause the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method).
	 * @since 1.0
	 */
	public ExistingRecordPersistedException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new ExistingRecordPersistedException with the specified detail message, cause, suppression enabled or disabled,
	 * and writable stack trace enabled or disabled.
	 *
	 * @param message              the detail message (which is saved for later retrieval by the getMessage() method).
	 *                             It should contain specific information about the record that already exists.
	 * @param cause                the cause (which is saved for later retrieval by the getCause() method).
	 *                             A null value is permitted and indicates that the cause is nonexistent or unknown.
	 * @param enableSuppression    whether or not suppression is enabled or disabled
	 *                             (a suppressed exception is an exception that is not propagated through the stacktrace).
	 * @param writableStackTrace  whether or not the stack trace should be writable
	 *                             (a writable stack trace allows the modification of the stack trace elements).
	 */
	public ExistingRecordPersistedException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
