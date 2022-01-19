package io.github.ninobomba.commons.exceptions.process;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TransactionProcessException extends RuntimeException implements BusinessException
{
    private static final String DEFAULT_MESSAGE = "TransactionProcessException - Error while executing a transaction process: ";

    @NotBlank
    private final String message;

    @NotBlank
    private final String description;

    private TransactionProcessException(String message, String description, Throwable exception) {
        super( DEFAULT_MESSAGE.concat( message ), exception );
        this.message = message;
        this.description = description;
    }

    public static TransactionProcessException create( String message ) {
        return create( message, null, null );
    }

    public static TransactionProcessException create( String message, String description ) {
        return create( message, description, null );
    }

    public static TransactionProcessException create( String message, String description, Throwable throwable ) {
        return new TransactionProcessException( message, description, throwable );
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
