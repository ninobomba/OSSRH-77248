package io.github.ninobomba.commons.exceptions.types.process;

import lombok.EqualsAndHashCode;


/**
 * Exception thrown when there is an error during the build process.
 * This exception extends the RuntimeException class.
 */
@EqualsAndHashCode ( callSuper = false )
public final class BuildProcessException extends RuntimeException {
	
	/**
	 * Exception thrown when there is an error during the build process.
	 * This exception extends the RuntimeException class.
	 *
	 * @see RuntimeException
	 */
	public BuildProcessException ( ) {
	}
	
	/**
	 * Constructs a new BuildProcessException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 */
	public BuildProcessException ( String message ) {
		super ( message );
	}
	
	/**
	 * Exception thrown during the build process.
	 *
	 * @param message a detailed message explaining the exception
	 * @param cause the cause of the exception
	 */
	public BuildProcessException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Constructs a new instance of BuildProcessException with the specified cause.
	 *
	 * @param cause the cause of this exception
	 */
	public BuildProcessException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Exception thrown when a build process encounters an error.
	 */
	public BuildProcessException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
