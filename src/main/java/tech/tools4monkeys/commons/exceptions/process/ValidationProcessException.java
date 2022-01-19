package tech.tools4monkeys.commons.exceptions.process;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ValidationProcessException extends RuntimeException implements BusinessException
{

    private static final String DEFAULT_MESSAGE = "ValidationProcessException - Error while validating data : ";

    @NotBlank
    private final String message;

    private final String description;

    private ValidationProcessException( String message, String description, Throwable exception )
    {
        super( DEFAULT_MESSAGE.concat( message ), exception );
        this.message = message;
        this.description = description;
    }

    public static ValidationProcessException create( String message ) {
        return create( message, null,null );
    }

    public static ValidationProcessException create( String message, String description ) {
        return create( message, description,null );
    }

    public static ValidationProcessException create( String message, String description, Throwable throwable ) {
        return new ValidationProcessException( message, description, throwable );
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
