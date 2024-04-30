package io.github.ninobomba.commons.exceptions.types.database;

import lombok.EqualsAndHashCode;

/**
 * Represents an exception that is thrown when an invalid parameter is provided.
 */
@EqualsAndHashCode ( callSuper = false )
public final class InvalidParameterProvidedException extends RuntimeException {
	
	/**
	 * Represents an exception that is thrown when an invalid parameter is provided.
	 */
	public InvalidParameterProvidedException ( ) {
	}
	
	/**
	 * Exception thrown when an invalid parameter is provided.
	 *
	 * @param message A string representing the detailed message of the exception.
	 */
	public InvalidParameterProvidedException ( String message ) {
		super ( message );
	}
	
	/**
	 * Exception thrown when an invalid parameter is provided.
	 *
	 * @param message the detail message
	 * @param cause the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method).
	 */
	public InvalidParameterProvidedException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Creates a new instance of InvalidParameterProvidedException with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public InvalidParameterProvidedException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Represents an exception that is thrown when an invalid parameter is provided.
	 */
	public InvalidParameterProvidedException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
