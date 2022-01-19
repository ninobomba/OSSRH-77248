package tech.tools4monkeys.commons.exceptions.process;

import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
public class BuildProcessException extends RuntimeException implements BusinessException
{
    private static final String DEFAULT_MESSAGE = "BuildProcessException - Error while executing build process: ";

    @NotBlank
    private final String message;

    @NotBlank
    private final String description;

    private BuildProcessException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static BuildProcessException create( String message ) {
        return create( message, null, null );
    }

    public static BuildProcessException create( String message, String description ) {
        return create( message, description, null );
    }

    public static BuildProcessException create( String message, String description, Throwable throwable ) {
        return new BuildProcessException( message, description, throwable );
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
