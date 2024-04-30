package io.github.ninobomba.commons.exceptions.types.api;

import lombok.EqualsAndHashCode;

/**
 * Exception thrown when an unexpected response is received from the API client.
 */
@EqualsAndHashCode ( callSuper = false )
public final class UnexpectedApiClientResponseException extends RuntimeException {
	
	/**
	 * Exception thrown when an unexpected response is received from the API client.
	 */
	public UnexpectedApiClientResponseException ( ) {
	}
	
	/**
	 * Creates a new instance of the {@code UnexpectedApiClientResponseException} class with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method).
	 */
	public UnexpectedApiClientResponseException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new UnexpectedApiClientResponseException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the getCause() method).
	 */
	public UnexpectedApiClientResponseException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Constructs a new UnexpectedApiClientResponseException with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public UnexpectedApiClientResponseException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * This exception is thrown when an unexpected response is received from an API client.
	 *
	 * @param message             the detail message
	 * @param cause               the cause of the exception
	 * @param enableSuppression   whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public UnexpectedApiClientResponseException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
