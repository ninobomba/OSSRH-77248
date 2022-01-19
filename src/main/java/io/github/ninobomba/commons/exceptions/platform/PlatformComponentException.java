package io.github.ninobomba.commons.exceptions.platform;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PlatformComponentException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "PlatformComponentException - Core Infrastructure Component Error :";

    @NotBlank
    private final String message;

    private final String description;

    private PlatformComponentException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static PlatformComponentException create( String message ) {
        return create( message, null, null );
    }

    public static PlatformComponentException create(String message, String description ) {
        return create( message, description, null );
    }

    public static PlatformComponentException create( String message, String description, Throwable throwable ) {
        return new PlatformComponentException( message, description, throwable );
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
