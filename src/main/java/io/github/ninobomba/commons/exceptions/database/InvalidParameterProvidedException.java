package io.github.ninobomba.commons.exceptions.database;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * This class represents an exception that is thrown when an invalid parameter is provided for a certain process.
 *
 * <p>
 * The {@code InvalidParameterProvidedException} class extends the {@code RuntimeException} class,
 * making it an unchecked exception. It provides a way to specify an error message and a description of the error.
 *
 * <p>
 * The class provides two static factory methods to create instances of the exception:
 * <ul>
 *   <li>{@link #create(String, String)}: Creates an instance with the specified error message and description.</li>
 *   <li>{@link #create(String, String, Throwable)}: Creates an instance with the specified error message, description, and cause.</li>
 * </ul>
 *
 * <p>
 * The exception also overrides the {@code toString()} method to provide a string representation of the exception,
 * including the error message and description.
 *
 * <p>
 * This class also makes use of the Project Lombok {@code @Getter} annotation to automatically generate getter methods
 * for the {@code message} and {@code description} fields.
 */
@Getter
public class InvalidParameterProvidedException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "InvalidParameterProvidedException - Invalid parameter provided for this process ";

    @NotBlank
    private final String message;

    private final String description;

    /**
     * Constructs a new InvalidParameterProvidedException with the specified message, description, and cause.
     *
     * @param message     the detail message
     * @param description the description of the invalid parameter
     * @param throwable   the cause of the exception
     */
    private InvalidParameterProvidedException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    /**
     * Creates a new instance of InvalidParameterProvidedException with the specified message and description.
     *
     * @param message     the detail message
     * @param description the description of the invalid parameter
     * @return a new instance of InvalidParameterProvidedException
     */
    public static InvalidParameterProvidedException create(String message, String description ) {
        return create( message, description, null );
    }

    /**
     * Creates a new instance of InvalidParameterProvidedException with the specified message, description, and throwable.
     *
     * @param message     the detail message
     * @param description the description of the invalid parameter
     * @param throwable   the cause of the exception
     * @return a new instance of InvalidParameterProvidedException
     */
    public static InvalidParameterProvidedException create( String message, String description, Throwable throwable ) {
        return new InvalidParameterProvidedException( message, description, throwable );
    }

    /**
     * Returns a string representation of the InvalidParameterProvidedException object.
     * This includes the default message, the message, and the description of the exception.
     *
     * @return a string representation of the InvalidParameterProvidedException
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
