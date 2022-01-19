package io.github.ninobomba.commons.exceptions.platform;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PlatformPostgresqlException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "PlatformPostgresqlException - Postgresql Error : ";

    @NotBlank
    private final String message;

    @NotBlank
    private final String description;

    private PlatformPostgresqlException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static PlatformPostgresqlException create( String message ) {
        return create( message, null, null );
    }

    public static PlatformPostgresqlException create(String message, String description ) {
        return create( message, description, null );
    }

    public static PlatformPostgresqlException create( String message, String description, Throwable throwable ) {
        return new PlatformPostgresqlException( message, description, throwable );
    }

    @Override
    public String toString()
    {
        return ""
                .concat( "--------------------------------------------------------------------" )
                .concat( "\n" )
                .concat( DEFAULT_MESSAGE ).concat( "\n" )
                .concat( "message: " + message ).concat( "\n" )
                .concat( "description: postgresql error: " + description ).concat( "\n" )
                .concat( "--------------------------------------------------------------------" )
                ;
    }

}
