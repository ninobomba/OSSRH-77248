package tech.tools4monkeys.commons.exceptions.commons;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class InvalidParameterException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "InvalidParameterException - Invalid parameter detected during the process: ";

    @NotBlank
    private final String message;

    private final String description;

    private InvalidParameterException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static InvalidParameterException create( String message ) {
        return create( message, null, null );
    }

    public static InvalidParameterException create( String message, String description ) {
        return create( message, description, null );
    }

    public static InvalidParameterException create( String message, String description, Throwable throwable ) {
        return new InvalidParameterException( message, description, throwable );
    }

    @Override
    public String toString()
    {
        return ""
                .concat( "--------------------------------------------------------------------" )
                .concat( "\n" )
                .concat( DEFAULT_MESSAGE ).concat( "\n" )
                .concat( "Message: " + message ).concat( "\n" )
                .concat( "Description - invalid parameter: [" + description + "]" ).concat( "\n" )
                .concat( "--------------------------------------------------------------------" );
    }
}
