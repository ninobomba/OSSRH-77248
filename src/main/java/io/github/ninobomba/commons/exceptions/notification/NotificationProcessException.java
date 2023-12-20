package io.github.ninobomba.commons.exceptions.notification;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * Exception class for errors that occur during the execution of a notification process.
 */
@Getter
public class NotificationProcessException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "NotificationProcessException - Error while executing a notification process: ";

    @NotBlank
    private final String message;

    private final String description;

    /**
     * Constructs a new NotificationProcessException with the specified message, description, and cause.
     *
     * @param message The error message associated with the exception.
     * @param description Additional details describing the exception.
     * @param throwable The cause of the exception.
     */
    private NotificationProcessException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    /**
     * Creates a new instance of the NotificationProcessException with the specified message.
     *
     * @param message The error message associated with the exception.
     * @return A new instance of the NotificationProcessException.
     */
    public static NotificationProcessException create( String message ) {
        return create( message, null, null );
    }

    /**
     * Creates a new instance of the NotificationProcessException with the specified message and description.
     *
     * @param message     The error message associated with the exception.
     * @param description The detailed description of the exception.
     * @return A new instance of the NotificationProcessException.
     */
    public static NotificationProcessException create(String message, String description ) {
        return create( message, description, null );
    }

    /**
     * Creates a new instance of the NotificationProcessException with the specified message, description, and throwable.
     *
     * @param message     The error message associated with the exception.
     * @param description The detailed description of the exception.
     * @param throwable   The Throwable object that caused this exception. It can be null if there is no underlying cause.
     * @return A new instance of the NotificationProcessException.
     */
    public static NotificationProcessException create( String message, String description, Throwable throwable ) {
        return new NotificationProcessException( message, description, throwable );
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
