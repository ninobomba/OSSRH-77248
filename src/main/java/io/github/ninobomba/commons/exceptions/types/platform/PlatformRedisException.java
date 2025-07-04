package io.github.ninobomba.commons.exceptions.types.platform;

import lombok.EqualsAndHashCode;

/**
 * Exception class for Redis related errors in the platform.
 * <p>
 * This exception is typically thrown when there is an error in the Redis operations
 * performed by the platform.
 */
@Deprecated ( forRemoval = true )
@EqualsAndHashCode ( callSuper = false )
public final class PlatformRedisException extends RuntimeException {

	/**
	 * Exception class for Redis related to errors in the platform.
	 * <p>
	 * This exception is typically thrown when there is an error in the Redis operations
	 * performed by the platform.
	 */
	public PlatformRedisException ( ) {
	}

	/**
	 * Constructs a new PlatformRedisException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 */
	public PlatformRedisException ( String message ) {
		super ( message );
	}

	/**
	 * Constructs a new PlatformRedisException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 * @param cause   the cause exception (which is saved for later retrieval by the getCause() method)
	 */
	public PlatformRedisException ( String message, Throwable cause ) {
		super ( message, cause );
	}

	/**
	 * Constructs a new PlatformRedisException with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public PlatformRedisException ( Throwable cause ) {
		super ( cause );
	}

	/**
	 * Constructs a new PlatformRedisException with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
	 *
	 * @param message            the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause              the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted and indicates that the cause is nonexistent
	 *                           or unknown.)
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public PlatformRedisException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
