package io.github.ninobomba.commons.exceptions.types.api;

import lombok.EqualsAndHashCode;


/**
 * The ApiClientResourceException class is a subclass of RuntimeException that represents exceptions
 * that occur when there is an issue with an API client resource. This class provides constructors
 * to create exception instances with different sets of parameters.
 */
@EqualsAndHashCode ( callSuper = false )
public final class ApiClientResourceException extends RuntimeException {
	
	
	/**
	 * The ApiClientResourceException class is a subclass of RuntimeException that represents exceptions
	 * that occur when there is an issue with an API client resource.
	 */
	public ApiClientResourceException ( ) {
	}
	
	/**
	 * Constructs a new ApiClientResourceException with the specified detail message.
	 *
	 * @param message the detail message of the exception
	 */
	public ApiClientResourceException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new {@code ApiClientResourceException} with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 */
	public ApiClientResourceException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Constructs a new ApiClientResourceException with the given cause.
	 *
	 * @param cause the cause of the exception
	 */
	public ApiClientResourceException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new ApiClientResourceException with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
	 *
	 * @param message               the detail message (which is saved for later retrieval by the getMessage() method).
	 * @param cause                 the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or
	 *  unknown.)
	 * @param enableSuppression     whether or not suppression is enabled or disabled
	 * @param writableStackTrace   whether or not the stack trace should be writable
	 */
	public ApiClientResourceException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
	
}
