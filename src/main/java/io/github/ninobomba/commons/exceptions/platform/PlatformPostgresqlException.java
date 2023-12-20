package io.github.ninobomba.commons.exceptions.platform;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * Represents a custom exception specific to Postgresql errors in the platform.
 */
@Getter
public class PlatformPostgresqlException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "PlatformPostgresqlException - Postgresql Error : ";

    @NotBlank
    private final String message;

    @NotBlank
    private final String description;

    /**
     * Constructs a new PlatformPostgresqlException with the specified message, description, and throwable.
     *
     * @param message the detail message.
     * @param description the additional description of the exception.
     * @param throwable the cause of the exception.
     */
    private PlatformPostgresqlException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    /**
     * Creates a new PlatformPostgresqlException with the specified message.
     *
     * @param message the message for the exception
     * @return a new PlatformPostgresqlException instance
     */
    public static PlatformPostgresqlException create( String message ) {
        return create( message, null, null );
    }

    /**
     * Create a new PlatformPostgresqlException with the specified message.
     *
     * @param message the error message associated with the exception
     * @return a new instance of the PlatformPostgresqlException
     */
    public static PlatformPostgresqlException create(String message, String description ) {
        return create( message, description, null );
    }

    /**
     * Creates a new instance of the PlatformPostgresqlException class with the specified message and description.
     *
     * @param message      The message associated with the exception.
     * @param description  The description of the exception.
     * @param throwable    The throwable object that caused this exception. (optional)
     * @return A new instance of the PlatformPostgresqlException class.
     */
    public static PlatformPostgresqlException create( String message, String description, Throwable throwable ) {
        return new PlatformPostgresqlException( message, description, throwable );
    }

    /**
     * Returns a string representation of the object.
     *
     * @return A string containing the message, description, and formatting characters.
     */
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
