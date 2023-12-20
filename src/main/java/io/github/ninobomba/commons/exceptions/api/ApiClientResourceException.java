package io.github.ninobomba.commons.exceptions.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.StringJoiner;


@Slf4j
public class ApiClientResourceException extends RuntimeException {

    private static final String DEFAULT_HEADER = ApiClientResourceException.class.getSimpleName() + " - ";

    private final String message;

    private final String description;

    /**
     * Creates a new instance of ApiClientResourceException.
     *
     * @param message      the error message.
     * @param description  the description of the error.
     * @param throwable    the throwable object that caused the error.
     */
    public ApiClientResourceException(String message, String description, Throwable throwable) {
        super( DEFAULT_HEADER.concat( message ).concat( StringUtils.isBlank( description ) ? "" : " - " + description ), throwable );
        this.message = message;
        this.description = description;
        log.error("{}", new String[]{ toString()}, throwable );
    }

    /**
     * Creates a new instance of ApiClientResourceException with the given error message.
     *
     * @param message the error message.
     * @return a new instance of ApiClientResourceException.
     */
    public static ApiClientResourceException create( String message ) {
        return create( message, new Throwable( message ) );
    }

    /**
     * Creates a new instance of ApiClientResourceException with the given error message
     * and throwable object.
     *
     * @param message   the error message.
     * @param throwable the throwable object associated with the exception.
     * @return a new instance of ApiClientResourceException.
     */
    public static ApiClientResourceException create( String message, Throwable throwable ) {
        return create( message, null, throwable );
    }

    /**
     * Creates a new instance of ApiClientResourceException with the given error message,
     * description, and throwable object.
     *
     * @param message     the error message.
     * @param description the description of the exception.
     * @param throwable   the throwable object associated with the exception.
     * @return a new instance of ApiClientResourceException.
     */
    public static ApiClientResourceException create( String message, String description, Throwable throwable ) {
        return new ApiClientResourceException( message, description, throwable );
    }

    /**
     * Returns a string representation of the object.
     *
     * The returned string represents the ApiClientResourceException object in a formatted way. It includes the default header, followed by the error message, description (if available), and a new line character separator between each component.
     *
     * @return a string representation of the ApiClientResourceException object.
     */
    @Override
    public String toString() {
        return new StringJoiner( System.lineSeparator() )
                .add( "" )
                .add( DEFAULT_HEADER )
                .add( "message: " + message )
                .add( Objects.isNull( description ) ? "" : "description: " + description )
                .add( "" )
                .toString();
    }
}
