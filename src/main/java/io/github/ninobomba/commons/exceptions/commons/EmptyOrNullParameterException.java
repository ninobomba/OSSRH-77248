package io.github.ninobomba.commons.exceptions.commons;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class EmptyOrNullParameterException extends RuntimeException
{

    private static final String DEFAULT_MESSAGE = "EmptyOrNullParameterException - Empty or null value provided during the process: ";

    @NotBlank
    private final String message;

    private final String description;

    /**
     * Exception class to be thrown when an empty or null parameter is encountered.
     * This exception can be used to handle cases where a method is expecting a non-empty
     * or non-null parameter and receives an empty or null value instead.
     *
     * <p>The EmptyOrNullParameterException extends the built-in Java Exception class,
     * allowing for easy integration into existing exception handling mechanisms.</p>
     *
     * <p>When instantiating an EmptyOrNullParameterException object, the caller must provide
     * a message, a description, and a throwable (optional). The message should provide
     * information about the specific parameter that was empty or null. The description
     * can provide additional contextual information about the error. The throwable,
     * if provided, can be used to chain the exception with other exceptions.</p>
     *
     * <p>Example Usage:</p>
     * <pre>{@code
     * void someMethod(String param1, int param2) throws EmptyOrNullParameterException {
     *     if (param1 == null || param1.isEmpty()) {
     *         throw new EmptyOrNullParameterException("param1", "Parameter 1 cannot be empty or null");
     *     }
     *     // rest of the method logic
     * }
     * }</pre>
     *
     * @param message A message explaining which parameter was empty or null.
     * @param description Additional contextual information about the error.
     * @param throwable A Throwable object to chain this exception with another.
     */
    private EmptyOrNullParameterException( String message, String description, Throwable throwable )
    {
        super( DEFAULT_MESSAGE.concat( message ), throwable );
        this.message = message;
        this.description = description;
    }

    public static EmptyOrNullParameterException create( String message ) {
        return create( message, null, null );
    }

    public static EmptyOrNullParameterException create( String message, String description ) {
        return create( message, description, null );
    }

    public static EmptyOrNullParameterException create( String message, String description, Throwable throwable ) {
        return new EmptyOrNullParameterException( message, description, throwable );
    }

    /**
     * Returns a string representation of the EmptyOrNullParameterException object.
     * The returned string includes the default message, the specific message, and the description
     * of the empty or null parameter that caused the exception.
     *
     * <p>Example Usage:</p>
     * <pre>{@code
     * try {
     *     throw new EmptyOrNullParameterException("param1", "Parameter 1 cannot be empty or null");
     * } catch (EmptyOrNullParameterException e) {
     *     String errorMessage = e.toString();
     *     System.out.println(errorMessage);
     * }
     * }</pre>
     *
     * @return A string representation of the EmptyOrNullParameterException object.
     */
    @Override
    public String toString()
    {
        return ""
                .concat( "--------------------------------------------------------------------" )
                .concat( "\n" )
                .concat( DEFAULT_MESSAGE ).concat( "\n" )
                .concat( "Message: " + message ).concat( "\n" )
                .concat( "Description - empty or null parameter: [" + description + "]" ).concat( "\n" )
                .concat( "--------------------------------------------------------------------" );
    }
}
