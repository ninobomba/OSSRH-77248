package io.github.ninobomba.commons.exceptions.platform;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * Represents an exception thrown by the PlatformComponent class.
 *
 * <p>
 * This exception is a subclass of RuntimeException and is used to handle errors related to the Core Infrastructure Component.
 * </p>
 *
 * <p>
 * The PlatformComponentException class provides methods to create exceptions with custom messages, descriptions, and causes.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>{@code
 * try {
 *     // code that may throw PlatformComponentException
 * } catch (PlatformComponentException e) {
 *     // handle exception
 * }
 * }</pre>
 *
 * @since 1.0
 */
@Getter
public class PlatformComponentException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "PlatformComponentException - Core Infrastructure Component Error :";

    @NotBlank
    private final String message;

    private final String description;

    /**
     * Constructs a new PlatformComponentException with the specified message, description and cause.
     *
     * @param message the detail message.
     * @param description the description of the exception.
     * @param throwable the cause of the exception (optional).
     */
    private PlatformComponentException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    /**
     * Creates a new instance of PlatformComponentException with the given message.
     *
     * @param message the error message
     * @return a new instance of PlatformComponentException
     */
    public static PlatformComponentException create( String message ) {
        return create( message, null, null );
    }

    /**
     * Creates a new instance of PlatformComponentException with the given message and description.
     *
     * @param message     the error message
     * @param description the error description
     * @return a PlatformComponentException object
     */
    public static PlatformComponentException create(String message, String description ) {
        return create( message, description, null );
    }

    /**
     * Creates a new instance of PlatformComponentException with the given message, description, and throwable.
     *
     * @param message     the error message
     * @param description the error description
     * @param throwable   the throwable that caused the exception (optional)
     * @return a new instance of PlatformComponentException
     */
    public static PlatformComponentException create( String message, String description, Throwable throwable ) {
        return new PlatformComponentException( message, description, throwable );
    }

    /**
     * Returns a String representation of this object.
     *
     * @return a String representation of this object.
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
