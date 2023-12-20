package io.github.ninobomba.commons.exceptions.process;

import lombok.Getter;

import javax.validation.constraints.NotBlank;


/**
 * Custom exception class for build process errors.
 *
 * <p>
 * This exception can be thrown when an error occurs while executing a build process.
 * It extends {@link RuntimeException} and implements the {@link BusinessException} interface.
 * </p>
 *
 * <p>
 * The exception stores a message and a description of the error that occurred.
 * Both the message and description are mandatory and should not be blank.
 * </p>
 *
 * <p>
 * The exception provides static factory methods for creating instances of the exception.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>{@code
 * try {
 *     // Execute build process
 * } catch (Throwable t) {
 *     throw BuildProcessException.create("Error occurred during build process", "Unable to complete the build", t);
 * }
 * }</pre>
 */
@Getter
public class BuildProcessException extends RuntimeException implements BusinessException
{
    private static final String DEFAULT_MESSAGE = "BuildProcessException - Error while executing build process: ";

    @NotBlank
    private final String message;

    @NotBlank
    private final String description;

    /**
     * Creates a new instance of the BuildProcessException class.
     *
     * @param message     the error message to be displayed
     * @param description a detailed description of the error
     * @param throwable   the underlying cause of the exception
     */
    private BuildProcessException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    /**
     * Creates a new instance of BuildProcessException.
     *
     * @param message the error message for the exception
     * @return a new instance of BuildProcessException
     */
    public static BuildProcessException create( String message ) {
        return create( message, null, null );
    }

    /**
     * Creates a new instance of BuildProcessException with the specified message and description.
     *
     * @param message     the error message
     * @param description the detailed error description
     * @return a new instance of BuildProcessException
     */
    public static BuildProcessException create( String message, String description ) {
        return create( message, description, null );
    }

    /**
     * Creates a BuildProcessException with the given message and description.
     * If a throwable is provided, it is set as the cause of the exception.
     *
     * @param message the error message for the exception
     * @param description additional information about the exception
     * @param throwable the cause of the exception (optional)
     * @return a new instance of BuildProcessException
     */
    public static BuildProcessException create( String message, String description, Throwable throwable ) {
        return new BuildProcessException( message, description, throwable );
    }

    /**
     * Returns a string representation of the object.
     * The string representation consists of a header, the default message, and the message, description, and separator.
     *
     * @return a string representation of the object
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
