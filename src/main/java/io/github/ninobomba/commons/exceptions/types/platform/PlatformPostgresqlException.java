package io.github.ninobomba.commons.exceptions.types.platform;

import lombok.EqualsAndHashCode;

/**
 * Exception class representing an error specific to the PostgreSQL platform.
 */
@EqualsAndHashCode ( callSuper = false )
public final class PlatformPostgresqlException extends RuntimeException {
	
	/**
	 * Exception class representing an error specific to the PostgreSQL platform.
	 */
	public PlatformPostgresqlException ( ) {
	}
	
	/**
	 * Constructs a new PlatformPostgresqlException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 */
	public PlatformPostgresqlException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new PlatformPostgresqlException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause   the cause (which is saved for later retrieval by the Throwable.getCause() method).
	 */
	public PlatformPostgresqlException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Constructs a new PlatformPostgresqlException with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public PlatformPostgresqlException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new PlatformPostgresqlException with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or disabled.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the getCause() method).
	 * @param enableSuppression whether or not suppression is enabled or disabled.
	 * @param writableStackTrace whether or not the stack trace should be writable.
	 */
	public PlatformPostgresqlException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
