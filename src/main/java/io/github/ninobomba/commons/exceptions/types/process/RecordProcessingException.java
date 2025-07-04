package io.github.ninobomba.commons.exceptions.types.process;

/**
 * The RecordProcessingException class is an exception that is thrown when an error occurs during record processing.
 */
public class RecordProcessingException extends RuntimeException {
	
	/**
	 *
	 */
	public RecordProcessingException ( ) {
	}
	
	/**
	 * Exception thrown when an error occurs during record processing.
	 */
	public RecordProcessingException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new RecordProcessingException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method).
	 * @param cause the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method).
	 */
	public RecordProcessingException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Creates a new RecordProcessingException with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public RecordProcessingException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new {@code RecordProcessingException} with the specified detail message, cause, suppression enabled or disabled,
	 * and writable stack trace enabled or disabled.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null} value is permitted, and indicates that the cause is nonexistent
	 *  or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled.
	 * @param writableStackTrace whether or not the stack trace should be writable.
	 */
	public RecordProcessingException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
	
}
