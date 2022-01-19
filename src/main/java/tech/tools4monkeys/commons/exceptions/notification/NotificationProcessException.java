package tech.tools4monkeys.commons.exceptions.notification;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class NotificationProcessException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "NotificationProcessException - Error while executing a notification process: ";

    @NotBlank
    private final String message;

    private final String description;

    private NotificationProcessException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static NotificationProcessException create( String message ) {
        return create( message, null, null );
    }

    public static NotificationProcessException create(String message, String description ) {
        return create( message, description, null );
    }

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
