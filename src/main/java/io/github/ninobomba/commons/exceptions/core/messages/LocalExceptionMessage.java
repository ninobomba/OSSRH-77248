package io.github.ninobomba.commons.exceptions.core.messages;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.StringJoiner;

@EqualsAndHashCode
@Builder
public final class LocalExceptionMessage {

    private Object id;
    private String code;
    private String message;
    private String description;
    private ExceptionLevel level;

    private String separator;

    private Class<? extends Exception> tClass;

    private Throwable throwable;
    @Override
    public String toString() {
        return new StringJoiner( Objects.isNull( separator ) ? SEPARATORS.DEFAULT_SEPARATOR.value : separator )
                .add( "" )
                .add( "Invoking Class: " + ( Objects.nonNull( tClass ) ? tClass.getSimpleName() : "" ) )
                .add( "Level: " + ( Objects.nonNull( level ) ? "Level: " + level : "" ) )
                .add( "Id: " + ( Objects.nonNull( id ) ? id : "" ) )
                .add( "Code: " + ( Objects.nonNull( code ) ? code : "" ) )
                .add( "Message: " + ( Objects.nonNull( message ) ? message : "" ) )
                .add( "Description: " + ( Objects.nonNull( description ) ?  description : "" ) )
                .add( "" )
                .toString();
    }
    enum SEPARATORS {
        DEFAULT_SEPARATOR( " -- "),
        SYSTEM_SEPARATOR(System.lineSeparator());

        final String value;
        SEPARATORS( String value ) {
            this.value = value;
        }
    }

    enum ExceptionLevel {
        CRITICAL, ERROR, WARNING, INFO, DEBUG, TRACE
    }

    @SneakyThrows
     public Exception create() {
        return Objects.nonNull( throwable ) ?
                tClass.getConstructor( String.class, Throwable.class ).newInstance( toString(), throwable ) :
                tClass.getConstructor( String.class ).newInstance( toString() );
    }

}
