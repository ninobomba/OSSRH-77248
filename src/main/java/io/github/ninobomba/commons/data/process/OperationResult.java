package io.github.ninobomba.commons.data.process;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public sealed interface OperationResult < T >
		permits OperationResult.Success, OperationResult.Failure {

	// Existing generic factories (kept for compatibility)
	static < T > OperationResult < T > success ( T data ) {
		Objects.requireNonNull ( data, "data must not be null; use successUnit() for unit-success" );
		return new Success <> ( data );
	}

	static < T > OperationResult < T > failure ( List < String > messages ) {
		return new Failure <> ( messages );
	}

	// New convenience factories mirroring typical Result patterns
	static OperationResult < Void > successUnit ( ) {
		return new Success <> ( null );
	}

	static < T > OperationResult < T > failure ( String... messages ) {
		Objects.requireNonNull ( messages, "messages must not be null" );
		return new Failure <> ( Arrays.asList ( messages ) );
	}

	// Helpers
	default boolean isSuccess ( ) {
		return this instanceof Success < ? >;
	}

	default boolean isFailure ( ) {
		return this instanceof Failure < ? >;
	}

	default T getOrNull ( ) {
		return ( this instanceof Success < T > ( T data ) ) ? data : null;
	}

	default T getOrThrow ( ) {
		if ( this instanceof Success < T > ( T data ) ) return data;
		if ( this instanceof Failure < T > ( List < String > messages ) ) {
			throw new IllegalStateException ( "OperationResult failed with messages: " + messages );
		}
		throw new IllegalStateException ( "Unknown OperationResult state" );
	}

	// Success record: immutable carrier of value (can be Void for unit-success)
	record Success < T >( T data ) implements OperationResult < T > {
		public Success {
			// Allow null to represent unit-success; enforce non-null if your domain requires:
			// Objects.requireNonNull(data, "data must not be null");
		}
	}

	// Failure record: immutable, defensively-copied messages
	record Failure < T >( List < String > messages ) implements OperationResult < T > {
		public Failure {
			Objects.requireNonNull ( messages, "messages must not be null" );
			if ( messages.stream ( ).anyMatch ( Objects::isNull ) ) {
				throw new NullPointerException ( "messages must not contain null elements" );
			}
			messages = List.copyOf ( messages );
		}

		public Failure ( String message ) {
			this ( Collections.singletonList ( Objects.requireNonNull ( message, "message must not be null" ) ) );
		}
	}
}
