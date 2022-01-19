package tech.tools4monkeys.commons.exceptions.database;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class InvalidParameterProvidedException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "InvalidParameterProvidedException - Invalid parameter provided for this process ";

    @NotBlank
    private final String message;

    private final String description;

    private InvalidParameterProvidedException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static InvalidParameterProvidedException create(String message, String description ) {
        return create( message, description, null );
    }

    public static InvalidParameterProvidedException create( String message, String description, Throwable throwable ) {
        return new InvalidParameterProvidedException( message, description, throwable );
    }

    @Override
    public String toString()
    {
        return ""
                .concat( "--------------------------------------------------------------------" )
                .concat( "\n" )
                .concat( DEFAULT_MESSAGE ).concat( "\n" )
                .concat( "message: " + message ).concat( "\n" )
                .concat( "description: " + description ).concat( "\n" )
                .concat( "--------------------------------------------------------------------" )
                ;
    }

}
