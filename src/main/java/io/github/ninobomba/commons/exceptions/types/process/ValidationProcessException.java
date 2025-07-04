package io.github.ninobomba.commons.exceptions.types.process;

import lombok.EqualsAndHashCode;

/**
 * The ValidationProcessException class is an exception that is thrown when a validation process encounters an error.
 * It extends the RuntimeException class and provides constructors to create instances of the exception with different
 * parameters.
 */
@EqualsAndHashCode ( callSuper = false )
public final class ValidationProcessException extends RuntimeException {
	
	/**
	 * An exception that is thrown when a validation process encounters an error.
	 */
	public ValidationProcessException ( ) {
	}
	
	/**
	 * Exception thrown during the validation process.
	 */
	public ValidationProcessException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new ValidationProcessException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the getCause() method).
	 */
	public ValidationProcessException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Creates a new instance of ValidationProcessException with the specified cause.
	 *
	 * @param cause the cause of this exception
	 */
	public ValidationProcessException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new ValidationProcessException with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or disabled.
	 * <p>
	 * This constructor is used to create a ValidationProcessException with additional information
	 * about the cause of the exception, whether or not suppression is enabled or disabled, and
	 * whether or not the stack trace should be writable.
	 *
	 * @param message              the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause                the cause (which is saved for later retrieval by the {@link #getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent
	 *  or unknown.)
	 * @param enableSuppression    whether or not suppression is enabled or disabled
	 * @param writableStackTrace  whether or not the stack trace should be writable
	 */
	public ValidationProcessException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
