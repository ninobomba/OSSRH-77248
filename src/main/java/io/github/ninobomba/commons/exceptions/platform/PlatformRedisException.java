package io.github.ninobomba.commons.exceptions.platform;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * Represents a custom exception class for Redis errors in the platform.
 *
 * This exception is a subclass of RuntimeException and includes additional fields
 * for the error message and description.
 *
 * @since 1.0
 */
@Getter
public class PlatformRedisException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "PlatformRedisException - Redis Error: ";

    @NotBlank
    private final String message;

    @NotBlank
    private final String description;

    /**
     * Constructs a new PlatformRedisException with the given message, description, and cause.
     *
     * @param message     the detailed message for the exception
     * @param description a description of the exception
     * @param throwable   the cause of the exception
     */
    private PlatformRedisException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    /**
     * Creates a new instance of PlatformRedisException with the given message.
     *
     * @param message the error message associated with the exception
     * @return a new instance of PlatformRedisException
     */
    public static PlatformRedisException create( String message ) {
        return create( message, null, null );
    }

    /**
     * Create a PlatformRedisException with the given message.
     *
     * @param message      The error message for the exception.
     * @return The created PlatformRedisException with the specified message.
     */
    public static PlatformRedisException create( String message, String description ) {
        return create( message, description, null );
    }

    /**
     * Creates a new PlatformRedisException with the given message, description, and optional throwable.
     *
     * @param message The message of the exception.
     * @param description The description of the exception.
     * @param throwable The optional throwable to be included in the exception.
     * @return The newly created PlatformRedisException.
     */
    public static PlatformRedisException create( String message, String description, Throwable throwable ) {
        return new PlatformRedisException( message, description, throwable );
    }

    /**
     * Returns a string representation of the object.
     * The string representation includes the default message, the message, and the description of the PlatformRedisException.
     *
     * @return the string representation of the PlatformRedisException object
     */
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
