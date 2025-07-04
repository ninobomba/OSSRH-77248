package io.github.ninobomba.commons.exceptions.types.notification;

import lombok.EqualsAndHashCode;

/**
 * Exception thrown to indicate an error in the notification process.
 * This exception extends the RuntimeException class, which means that it is an unchecked exception.
 * Unchecked exceptions do not need to be declared in a method's throw clause or caught using a try-catch block.
 */
@EqualsAndHashCode ( callSuper = false )
public final class NotificationProcessException extends RuntimeException {

	/**
	 * Exception thrown to indicate an error in the notification process.
	 * This exception extends the RuntimeException class, which means that it is an unchecked exception.
	 * Unchecked exceptions do not need to be declared in a method's throws clause or caught using a try-catch block.
	 * <p>
	 * Example usage:
	 * <p>
	 * try {
	 * // perform a notification process
	 * } catch (NotificationProcessException e) {
	 * // handle exception
	 * }
	 */
	public NotificationProcessException ( ) {
	}

	/**
	 * Creates a new instance of NotificationProcessException with the given message.
	 *
	 * @param message the detail message of the exception
	 */
	public NotificationProcessException ( String message ) {
		super ( message );
	}

	/**
	 * Represents an exception thrown during the notification process.
	 * It is a type of RuntimeException, indicating that it doesn't need to be declared in method signature or caught explicitly.
	 */
	public NotificationProcessException ( String message, Throwable cause ) {
		super ( message, cause );
	}

	/**
	 * Constructs a new NotificationProcessException with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public NotificationProcessException ( Throwable cause ) {
		super ( cause );
	}

	/**
	 * NotificationProcessException is an exception class that is thrown when an error occurs during the processing of notifications.
	 * This exception extends the RuntimeException class.
	 */
	public NotificationProcessException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
