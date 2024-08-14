package io.github.ninobomba.commons.exceptions.core.messages;

import lombok.Builder;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The LocalExceptionMessageBuilder class is responsible for building exception messages with various details.
 */
@Builder
public final class LocalExceptionMessageBuilder {


	private String id;

	private String code;

	// Brief summary of the error
	private String message;

	// Detailed explanation of the error
	private String description;

	private String invokingClass;

	private String separator;

	// The URI, which caused the problem
	private String instance;

	// The URI providing more details about the problem
	private String link;

	private ExceptionLevel level;

	private Throwable throwable;

	private Map < String, Serializable > properties;

	/**
	 * Returns a string representation of the LocalExceptionMessageBuilder object.
	 *
	 * @return the string representation of the object
	 */
	@Override
	public String toString ( ) {
		updateWithPropertiesIfRequired ( );
		return new StringJoiner ( Objects.isNull ( separator ) ? SEPARATORS.DEFAULT_SEPARATOR.value : separator )
				.add ( "" )
				.add ( "Invoking Class: " + ( Objects.nonNull ( invokingClass ) ? invokingClass : "" ) )
				.add ( "Level: " + ( Objects.nonNull ( level ) ? level : "" ) )
				.add ( "Id: " + ( Objects.nonNull ( id ) ? id : "" ) )
				.add ( "Code: " + ( Objects.nonNull ( code ) ? code : "" ) )
				.add ( "Message: " + ( Objects.nonNull ( message ) ? message : "" ) )
				.add ( "Description: " + ( Objects.nonNull ( description ) ? description : "" ) )
				.add ( "Instance: " + ( Objects.nonNull ( instance ) ? instance : "" ) )
				.add ( "Link: " + ( Objects.nonNull ( link ) ? link : "" ) )
				.add ( "" )
				.toString ( );
	}

	private void updateWithPropertiesIfRequired ( ) {
		if ( Objects.isNull ( properties ) || properties.isEmpty ( ) ) return;

		id = String.valueOf ( properties.getOrDefault ( "id", id ) );
		code = String.valueOf ( properties.getOrDefault ( "code", code ) );
		message = String.valueOf ( properties.getOrDefault ( "message", message ) );
		description = String.valueOf ( properties.getOrDefault ( "description", description ) );
		invokingClass = String.valueOf ( properties.getOrDefault ( "invokingClass", invokingClass ) );
		separator = String.valueOf ( properties.getOrDefault ( "separator", SEPARATORS.DEFAULT_SEPARATOR.value ) );
		instance = String.valueOf ( properties.getOrDefault ( "instance", instance ) );
		link = String.valueOf ( properties.getOrDefault ( "link", link ) );
		level = ExceptionLevel.valueOf ( String.valueOf ( properties.getOrDefault ( "level", level == null ? ExceptionLevel.INFO : level.name ( ) ) ) );
	}

	/**
	 * The SEPARATORS enum defines different separators that can be used in exception messages.
	 */
	public enum SEPARATORS {
		DEFAULT_SEPARATOR ( ", " ),
		SYSTEM_SEPARATOR ( System.lineSeparator ( ) );
		public final String value;

		SEPARATORS ( String value ) {
			this.value = value;
		}
	}

	/**
	 * This enum represents the level of an exception. It can have one
	 * of the following values: CRITICAL, ERROR, WARNING, INFO, DEBUG, TRACE.
	 */
	public enum ExceptionLevel {
		CRITICAL, ERROR, WARNING, INFO, DEBUG, TRACE
	}

	/**
	 * Creates an instance of the specified exception type.
	 *
	 * @param type the class representing the exception type
	 * @param <T>  the type of RuntimeException
	 * @return an instance of the specified exception type
	 * @throws T if the exception cannot be instantiated
	 */
	@SneakyThrows
	public < T extends RuntimeException > T create ( Class < T > type ) {
		this.invokingClass = type.getSimpleName ( );
		return Objects.isNull ( throwable ) ?
				type.getConstructor ( String.class ).newInstance ( toString ( ) ) :
				type.getConstructor ( String.class, Throwable.class ).newInstance ( toString ( ), throwable );
	}

}
