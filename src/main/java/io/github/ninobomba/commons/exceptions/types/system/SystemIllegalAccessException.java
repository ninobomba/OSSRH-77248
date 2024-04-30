package io.github.ninobomba.commons.exceptions.types.system;

import lombok.EqualsAndHashCode;

/**
 * The SystemIllegalAccessException is a subclass of RuntimeException and represents an exception
 * that is thrown when there is an unauthorized access to a system.
 */
@EqualsAndHashCode ( callSuper = false )
public final class SystemIllegalAccessException extends RuntimeException {
	
	/**
	 * Constructs a new SystemIllegalAccessException with no detail message.
	 */
	public SystemIllegalAccessException ( ) {
	}
	
	/**
	 * Constructs a new SystemIllegalAccessException with the specified detail message.
	 *
	 * @param message the detail message. It is used to provide information about the exception.
	 * @throws NullPointerException if the message is null.
	 */
	public SystemIllegalAccessException ( String message ) {
		super ( message );
	}
	
	/**
	 * Constructs a new SystemIllegalAccessException with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown
	 * .)
	 */
	public SystemIllegalAccessException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * Constructs a new SystemIllegalAccessException with the specified throwable as the cause.
	 *
	 * @param cause the cause of the exception
	 */
	public SystemIllegalAccessException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * Constructs a new SystemIllegalAccessException with the specified detail message, cause, suppression enabled or disabled,
	 * and writable stack trace enabled or disabled.
	 *
	 * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted,
	 * 		and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled (A suppressed exception is an exception that
	 * 		is not propagated because another exception occurred. It can be retrieved by the Throwable.getSuppressed method.)
	 * @param writableStackTrace whether or not the stack trace should be writable (A writable stack trace allows a program
	 * 		to create instances of StackTraceElement that might not have been created by the VM.)
	 */
	public SystemIllegalAccessException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
	
}
