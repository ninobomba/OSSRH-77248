package tech.tools4monkeys.commons.exceptions.process;

public class AbstractFactoryBusinessException
{

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
