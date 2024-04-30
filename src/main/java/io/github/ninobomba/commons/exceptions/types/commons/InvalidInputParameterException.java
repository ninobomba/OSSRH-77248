package io.github.ninobomba.commons.exceptions.types.commons;

import lombok.EqualsAndHashCode;

/**
 * The InvalidInputParameterException class is a custom exception that is thrown when an invalid input parameter is detected. This exception extends the RuntimeException class, meaning
 *  it is an unchecked exception that does not need to be declared or caught explicitly.
 *
 * This class provides several constructors to allow different ways of creating an InvalidInputParameterException object, depending on the specific scenario where it is being used
 * .
 *
 * Example usages:
 * throw new InvalidInputParameterException();
 * throw new InvalidInputParameterException("Invalid input parameter");
 * throw new InvalidInputParameterException("Invalid input parameter", throwable);
 *
 * @see RuntimeException
 */
@EqualsAndHashCode ( callSuper = false )
public final class InvalidInputParameterException extends RuntimeException {
	
	/**
	 * The InvalidInputParameterException class is a custom exception that is thrown when an invalid input parameter is detected. This exception extends the RuntimeException class.
	 *
	 * This class provides several constructors to allow different ways of creating an InvalidInputParameterException object, depending on the specific scenario where it is being used
	 * .
	 */
	public InvalidInputParameterException ( ) {
	}
	
	/**
	 * Represents an invalid input parameter exception. This exception is thrown when an invalid input parameter is detected.
	 * It extends the RuntimeException class and is an unchecked exception that does not need to be declared or caught explicitly.
	 */
	public InvalidInputParameterException ( String message ) {
		super ( message );
	}
	
	/**
	 * This exception is thrown when an invalid input parameter is detected.
	 */
	public InvalidInputParameterException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	/**
	 * InvalidInputParameterException is a custom exception class that is thrown when an invalid input parameter is detected.
	 * This exception extends the RuntimeException class and is an unchecked exception.
	 */
	public InvalidInputParameterException ( Throwable cause ) {
		super ( cause );
	}
	
	/**
	 * The InvalidInputParameterException class is a custom exception that is thrown when an invalid input parameter is detected. This exception extends the RuntimeException class, meaning
	 *
	 * it is an unchecked exception that does not need to be declared or caught explicitly.
	 *
	 * This class provides several constructors to allow different ways of creating an InvalidInputParameterException object, depending on the specific scenario where it is being used
	 * .
	 *
	 * Example usages:
	 * throw new InvalidInputParameterException();
	 * throw new InvalidInputParameterException("Invalid input parameter");
	 * throw new InvalidInputParameterException("Invalid input parameter", throwable);
	 *
	 * @see RuntimeException
	 */
	public InvalidInputParameterException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
