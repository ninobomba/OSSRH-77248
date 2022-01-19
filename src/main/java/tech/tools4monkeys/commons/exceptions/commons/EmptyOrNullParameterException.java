package tech.tools4monkeys.commons.exceptions.commons;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class EmptyOrNullParameterException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "EmptyOrNullParameterException - Empty or null value provided during the process: ";

    @NotBlank
    private final String message;

    private final String description;

    private EmptyOrNullParameterException( String message, String description, Throwable throwable )
    {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static EmptyOrNullParameterException create( String message ) {
        return create( message, null, null );
    }

    public static EmptyOrNullParameterException create( String message, String description ) {
        return create( message, description, null );
    }

    public static EmptyOrNullParameterException create( String message, String description, Throwable throwable ) {
        return new EmptyOrNullParameterException( message, description, throwable );
    }

    @Override
    public String toString()
    {
        return ""
                .concat( "--------------------------------------------------------------------" )
                .concat( "\n" )
                .concat( DEFAULT_MESSAGE ).concat( "\n" )
                .concat( "Message: " + message ).concat( "\n" )
                .concat( "Description - empty or null parameter: [" + description + "]" ).concat( "\n" )
                .concat( "--------------------------------------------------------------------" );
    }
}
