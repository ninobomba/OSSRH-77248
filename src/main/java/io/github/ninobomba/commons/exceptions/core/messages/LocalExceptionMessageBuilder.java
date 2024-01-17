package io.github.ninobomba.commons.exceptions.core.messages;

import lombok.Builder;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.StringJoiner;

@Builder
public final class LocalExceptionMessageBuilder {
	
	
	private String id;
	private String code;
	private String message;
	private String description;
	private String invokingClass;
	private String separator;
	
	private ExceptionLevel level;
	
	private Throwable throwable;
	
	@Override
	public String toString ( ) {
		return new StringJoiner ( Objects.isNull ( separator ) ? SEPARATORS.DEFAULT_SEPARATOR.value : separator )
				.add ( "" )
				.add ( "Invoking Class: " + ( Objects.nonNull ( invokingClass ) ? invokingClass : "" ) )
				.add ( "Level: " + ( Objects.nonNull ( level ) ? "Level: " + level : "" ) )
				.add ( "Id: " + ( Objects.nonNull ( id ) ? id : "" ) )
				.add ( "Code: " + ( Objects.nonNull ( code ) ? code : "" ) )
				.add ( "Message: " + ( Objects.nonNull ( message ) ? message : "" ) )
				.add ( "Description: " + ( Objects.nonNull ( description ) ? description : "" ) )
				.add ( "" )
				.toString ( );
	}
	
	public enum SEPARATORS {
		DEFAULT_SEPARATOR ( " -- " ),
		SYSTEM_SEPARATOR ( System.lineSeparator ( ) );
		
		final String value;
		
		SEPARATORS ( String value ) {
			this.value = value;
		}
	}
	
	public enum ExceptionLevel {
		CRITICAL, ERROR, WARNING, INFO, DEBUG, TRACE
	}
	
	@SneakyThrows
	public < T extends RuntimeException > T create ( Class < T > type ) {
		this.invokingClass = type.getSimpleName ( );
		return Objects.isNull ( throwable ) ?
				type.getConstructor ( String.class ).newInstance ( toString ( ) ) :
				type.getConstructor ( String.class, Throwable.class ).newInstance ( toString ( ), throwable );
	}
	
}
