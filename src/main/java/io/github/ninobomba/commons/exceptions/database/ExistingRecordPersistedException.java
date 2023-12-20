package io.github.ninobomba.commons.exceptions.database;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * The ExistingRecordPersistedException class is a custom exception that is thrown when there is an existing record in the database with a specific value.
 *
 * This class extends the RuntimeException class and includes the following properties:
 *   - message: The detail message of the exception.
 *   - description: The description of the exception providing more details.
 *
 * The ExistingRecordPersistedException class provides the following functionality:
 *   - Creation of a new instance with the specified detail message, description, and cause.
 *   - Creation of a new instance with the provided message.
 *   - Creation of a new instance with the provided message and description.
 *   - Creation of a new instance with the provided message, description, and throwable.
 *   - String representation of the object, which includes the default message, error message, description, and separator lines.
 *
 * @since 1.0
 */
@Getter
public class ExistingRecordPersistedException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "ExistingRecordPersistedException - There is an existing record in db with such value ";

    @NotBlank
    private final String message;

    private final String description;

    /**
     * Constructs a new <code>ExistingRecordPersistedException/code> with the specified detail message, description,
     * and cause.
     *
     * @param message     the detail message
     * @param description the description of the exception
     * @param throwable   the cause of the exception
     */
    private ExistingRecordPersistedException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    /**
     * Creates an instance of ExistingRecordPersistedException with the provided message.
     *
     * @param message The error message describing the existing record persistence exception.
     * @return A new instance of ExistingRecordPersistedException.
     */
    public static ExistingRecordPersistedException create(String message ) {
        return create( message, null, null );
    }

    /**
     * Creates an instance of ExistingRecordPersistedException with the provided message and description.
     *
     * @param message The error message describing the existing record persistence exception.
     * @param description The description providing more details about the existing record persistence exception.
     * @return A new instance of ExistingRecordPersistedException.
     */
    public static ExistingRecordPersistedException create(String message, String description ) {
        return create( message, description, null );
    }

    /**
     * Creates an instance of ExistingRecordPersistedException with the provided message, description, and throwable.
     *
     * @param message      The error message describing the existing record persistence exception.
     * @param description  The description providing more details about the existing record persistence exception.
     * @param throwable    The throwable object containing the cause of the existing record persistence exception.
     * @return A new instance of ExistingRecordPersistedException.
     */
    public static ExistingRecordPersistedException create( String message, String description, Throwable throwable ) {
        return new ExistingRecordPersistedException( message, description, throwable );
    }

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object, which includes the default message, error message, description, and separator lines.
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
