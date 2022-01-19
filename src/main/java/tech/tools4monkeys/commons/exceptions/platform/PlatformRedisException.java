package tech.tools4monkeys.commons.exceptions.platform;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PlatformRedisException extends RuntimeException
{
    private static final String DEFAULT_MESSAGE = "PlatformRedisException - Redis Error: ";

    @NotBlank
    private final String message;

    @NotBlank
    private final String description;

    private PlatformRedisException( String message, String description, Throwable throwable ) {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static PlatformRedisException create( String message ) {
        return create( message, null, null );
    }

    public static PlatformRedisException create( String message, String description ) {
        return create( message, description, null );
    }

    public static PlatformRedisException create( String message, String description, Throwable throwable ) {
        return new PlatformRedisException( message, description, throwable );
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
