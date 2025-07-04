package io.github.ninobomba.commons.exceptions.types.commons;

import lombok.EqualsAndHashCode;

/**
 * Exception class to represent the case when an empty or null parameter is encountered.
 */
@EqualsAndHashCode ( callSuper = false )
public final class EmptyOrNullParameterException extends RuntimeException {
	
	/**
	 * Exception class to represent the case when an empty or null parameter is encountered.
	 */
	public EmptyOrNullParameterException ( ) {
	}
	
	/**
	 * Exception class to represent the case when an empty or null parameter is encountered.
	 */
	public EmptyOrNullParameterException ( String message ) {
		super ( message );
	}
	
	/**
	 * Creates a new instance of EmptyOrNullParameterException with the specified message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 * @param cause the cause (which is saved for later retrieval by the getCause() method)
	 */
	public EmptyOrNullParameterException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 *
	 * Constructs a new EmptyOrNullParameterException with the specified cause.
	 *
	 * @param cause the cause for this exception
	 */
	public EmptyOrNullParameterException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new EmptyOrNullParameterException with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or disabled.
	 *
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method). A null value is permitted, and indicates that the cause is nonexistent or unknown
	 * .
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public EmptyOrNullParameterException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
