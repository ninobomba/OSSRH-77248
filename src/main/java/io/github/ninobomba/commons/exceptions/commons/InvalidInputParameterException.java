package io.github.ninobomba.commons.exceptions.commons;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
public class InvalidInputParameterException extends RuntimeException {


    private static final String DEFAULT_HEADER = InvalidInputParameterException.class.getSimpleName() + " - ";

    private final String message;

    private final String description;


    public InvalidInputParameterException(String message, String description, Throwable throwable) {
        super( DEFAULT_HEADER.concat( message ).concat( StringUtils.isBlank( description ) ? "" : " - " + description ), throwable );
        this.message = message;
        this.description = description;
        log.error("{}", new String[]{toString()}, throwable );
    }

    public static InvalidInputParameterException create( String message ) {
        return create( message, new Throwable( message ) );
    }

    public static InvalidInputParameterException create(String message, Throwable throwable ) {
        return create( message, null, throwable );
    }

    public static InvalidInputParameterException create(String message, String description, Throwable throwable) {
        return new InvalidInputParameterException( message, description, throwable);
    }

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
