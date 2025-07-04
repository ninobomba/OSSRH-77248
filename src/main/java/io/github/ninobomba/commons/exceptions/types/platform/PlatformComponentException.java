package io.github.ninobomba.commons.exceptions.types.platform;


import lombok.EqualsAndHashCode;

/**
 * PlatformComponentException is a custom exception class for platform-component-related exceptions.
 * It extends the RuntimeException class, making it an unchecked exception that does not need to be declared or caught.
 * This class provides constructors to create instances of PlatformComponentException with different parameters.
 */
@Deprecated ( forRemoval = true )
@EqualsAndHashCode ( callSuper = false )
public final class PlatformComponentException extends RuntimeException {

	/**
	 * A custom exception class for platform-component-related exceptions.
	 */
	public PlatformComponentException ( ) {
	}

	/**
	 * Constructs a new PlatformComponentException with the specified message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 */
	public PlatformComponentException ( String message ) {
		super ( message );
	}

	/**
	 * Constructs a new PlatformComponentException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method).
	 * @param cause   the cause (which is saved for later retrieval by the getCause() method).
	 */
	public PlatformComponentException ( String message, Throwable cause ) {
		super ( message, cause );
	}

	/**
	 * Constructs a new PlatformComponentException with the specified cause.
	 *
	 * @param cause the cause of this exception
	 */
	public PlatformComponentException ( Throwable cause ) {
		super ( cause );
	}

	public PlatformComponentException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
