package io.github.ninobomba.commons.exceptions.process;

/**
 * The BusinessExceptionAbstractFactory class is a utility class that provides methods for creating instances of BusinessException and its subclasses based on the ActionType enum.
 */
public class BusinessExceptionAbstractFactory
{

    /**
     * This is a private constructor for the BusinessExceptionAbstractFactory class.
     * It prevents the instantiation of the class.
     */
    private BusinessExceptionAbstractFactory() {}

    /**
     * Creates a BusinessException based on the specified ActionType, message, description, and throwable.
     *
     * @param type        the ActionType that defines the type of the BusinessException
     * @param message     the error message associated with the BusinessException
     * @param description the error description associated with the BusinessException
     * @param throwable   the Throwable object associated with the BusinessException
     * @return a BusinessException object based on the specified parameters, or null if the ActionType is unsupported
     */
    public static BusinessException create( ActionType type, String message, String description, Throwable throwable )
    {
        if ( type == ActionType.BUILD )
            return BuildProcessException.create( message, description, throwable );
        else if( type == ActionType.VALIDATION )
            return ValidationProcessException.create( message, description, throwable );
        else if( type ==  ActionType.TRANSACTION )
            return TransactionProcessException.create( message, description, throwable );

        return null;
    }


    /**
     * Creates a BusinessException based on the given ActionType, message, and description.
     *
     * @param type        The ActionType to determine the type of exception to create.
     * @param message     The message of the exception.
     * @param description The description of the exception.
     * @return A BusinessException based on the given parameters. If the ActionType is not recognized, null is returned.
     */
    public static BusinessException create( ActionType type, String message, String description )
    {
        if ( type == ActionType.BUILD )
            return BuildProcessException.create( message, description );
        else if( type == ActionType.VALIDATION )
            return ValidationProcessException.create( message, description  );
        else if( type == ActionType.TRANSACTION )
            return TransactionProcessException.create( message, description );

        return null;
    }

    public enum ActionType
    {
        BUILD,
        VALIDATION,
        TRANSACTION;
    }

}
