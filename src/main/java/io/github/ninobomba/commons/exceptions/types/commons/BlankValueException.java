package io.github.ninobomba.commons.exceptions.types.commons;

import lombok.EqualsAndHashCode;

/**
 * Exception thrown when a blank value is encountered.
 *
 * This exception extends*/
@EqualsAndHashCode ( callSuper = false )
public final class BlankValueException extends RuntimeException {
	
	/**
	 * Exception thrown when a blank value is encountered.
	 */
	public BlankValueException ( ) {
	}
	
	/**
	 * Constructs a new BlankValueException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 */
	public BlankValueException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new BlankValueException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method).
	 */
	public BlankValueException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Constructs a new BlankValueException with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public BlankValueException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new {@code BlankValueException} with the specified detail message, cause, suppression enabled or
	 * disabled, and writable stack trace enabled or disabled.
	 *
	 * @param message            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause              the cause (which is saved for later retrieval by the {@link #getCause()} method). (A null
	 *                           value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public BlankValueException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
