package io.github.ninobomba.commons.exceptions.database;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ExistingRecordPersistedException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "ExistingRecordPersistedException - There is an existing record in db with such value ";

    @NotBlank
    private final String message;

    private final String description;

    private ExistingRecordPersistedException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static ExistingRecordPersistedException create(String message ) {
        return create( message, null, null );
    }

    public static ExistingRecordPersistedException create(String message, String description ) {
        return create( message, description, null );
    }

    public static ExistingRecordPersistedException create( String message, String description, Throwable throwable ) {
        return new ExistingRecordPersistedException( message, description, throwable );
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
