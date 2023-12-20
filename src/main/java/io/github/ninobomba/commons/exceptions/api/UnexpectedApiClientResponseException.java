package io.github.ninobomba.commons.exceptions.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents an exception that is thrown when an unexpected response is received from the API client.
 * This class extends the RuntimeException class.
 */
@Slf4j
public class UnexpectedApiClientResponseException extends RuntimeException {


    private static final String DEFAULT_HEADER = UnexpectedApiClientResponseException.class.getSimpleName() + " - ";

    private final String message;

    private final String description;


    /**
     * Constructs a new UnexpectedApiClientResponseException with the specified message, description, and throwable.
     *
     * @param message     the error message.
     * @param description the description of the error.
     * @param throwable   the cause of the error.
     */
    public UnexpectedApiClientResponseException(String message, String description, Throwable throwable) {
        super( DEFAULT_HEADER.concat( message ).concat( StringUtils.isBlank( description ) ? "" : " - " + description ), throwable );
        this.message = message;
        this.description = description;
       log.error("{}", new String[]{toString()}, throwable );
    }

    /**
     * Creates a new UnexpectedApiClientResponseException with the specified message.
     *
     * @param message the error message.
     * @return the created UnexpectedApiClientResponseException object.
     */
    public static UnexpectedApiClientResponseException create(String message ) {
        return create( message, new Throwable( message ) );
    }

    /**
     * Creates a new UnexpectedApiClientResponseException with the specified message and throwable.
     *
     * @param message   the error message.
     * @param throwable the Throwable object to be wrapped.
     * @return the created UnexpectedApiClientResponseException object.
     */
    public static UnexpectedApiClientResponseException create(String message, Throwable throwable ) {
        return create( message, null, throwable );
    }

    /**
     * Creates a new UnexpectedApiClientResponseException with the specified message, description, and throwable.
     *
     * @param message     the error message.
     * @param description the error description.
     * @param throwable   the Throwable object to be wrapped.
     * @return the created UnexpectedApiClientResponseException object.
     */
    public static UnexpectedApiClientResponseException create(String message, String description, Throwable throwable) {
        return new UnexpectedApiClientResponseException( message, description, throwable);
    }

    /**
     * Returns the string representation of this object.
     *
     * @return the string representation of this object.
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
