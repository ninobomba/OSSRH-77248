package io.github.ninobomba.commons.patterns.process;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

public sealed interface OperationResult < T >
		permits OperationResult.Success, OperationResult.Failure {

	record Success < T >( T data ) implements OperationResult < T > {
	}

	record Failure < T >( T data ) implements OperationResult < T > {
	}

	static < T > OperationResult < T > success ( T value ) {
		return new Success <> ( value );
	}

	static < T > OperationResult < T > failure ( T value ) {
		return new Failure <> ( value );
	}
	
	default boolean isFailure ( ) {
		return this instanceof Failure < ? >;
	}

	default boolean isSuccess ( ) {
		return this instanceof Success < ? >;
	}

	default T getOrNull ( ) {
		if ( this instanceof Success < T > ( T data ) ) return data;
		if ( this instanceof Failure < T > ( T data ) ) return data;
		return null;
	}

	default T getOrElse ( T fallback ) {
		if ( this instanceof Success < T > ( T data ) ) return data;
		if ( this instanceof Failure < T > ( T data ) ) return data != null ? data : fallback;
		return fallback;
	}

	default T getOrThrow ( ) {
		return switch ( this ) {
			case Success < T > s -> s.data ( );
			case Failure < T > f -> throw OperationFailedException.of ( extractErrorMessages ( f.data ( ) ) );
		};
	}

	private static List < String > extractErrorMessages ( Object payload ) {
		if ( payload instanceof List < ? > list ) {
			@SuppressWarnings ( "unchecked" )
			List < String > messages = ( List < String > ) list;
			return messages;
		}
		return List.of ( "Operation failed" );
	}

	@Getter
	final class OperationFailedException extends RuntimeException {
		private final List < String > messages;

		public OperationFailedException ( List < String > messages ) {
			super ( String.join ( "; ", Objects.requireNonNull ( messages, "messages" ) ) );
			this.messages = List.copyOf ( messages );
		}

		public static OperationFailedException of ( List < String > messages ) {
			return new OperationFailedException ( messages );
		}
	}
}
