package io.github.ninobomba.commons.exceptions.types.process;

import lombok.EqualsAndHashCode;

/**
 * This class represents an exception that occurs during the transaction process.
 * It is a subclass of the RuntimeException class.
 */
@EqualsAndHashCode ( callSuper = false )
public final class TransactionProcessException extends RuntimeException {
	
	
	/**
	 * This class represents an exception that occurs during the transaction process.
	 * It is a subclass of the RuntimeException class.
	 *
	 * Example usage:
	 *    try {
	 *        // Some transaction process code
	 *    } catch (TransactionProcessException ex) {
	 *        // Handle the exception
	 *    }
	 */
	public TransactionProcessException ( ) {
	}
	
	/**
	 * Constructs a new TransactionProcessException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public TransactionProcessException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new TransactionProcessException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method)
	 * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method)
	 */
	public TransactionProcessException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Constructs a new TransactionProcessException with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public TransactionProcessException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new TransactionProcessException with the specified detail message, cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A null value is permitted, and indicates that the cause is nonexistent or unknown
	 * .)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public TransactionProcessException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
