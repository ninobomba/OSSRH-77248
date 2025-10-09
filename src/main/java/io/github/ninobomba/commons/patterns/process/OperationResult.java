package io.github.ninobomba.commons.patterns.process;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public sealed interface OperationResult < T >
		permits OperationResult.Success, OperationResult.Failure {

	// Existing generic factories (kept for compatibility)
	static < T > OperationResult < T > success ( T data ) {
		Objects.requireNonNull ( data, "data must not be null" );
		return new Success <> ( data );
	}

	static < T > OperationResult < T > failure ( List < String > messages ) {
		return new Failure <> ( messages );
	}

	// New convenience factories mirroring typical Result patterns
	// Renamed to avoid overload ambiguity with success(T) and to make intent explicit
	static OperationResult < Void > successUnit ( ) {
		return Success.unit ( );
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
			throw new OperationFailedException ( messages );
		}
		throw new IllegalStateException ( "Unknown OperationResult state" );
	}

	// Functional operations
	default < U > OperationResult < U > map ( java.util.function.Function < ? super T, ? extends U > mapper ) {
		Objects.requireNonNull ( mapper, "mapper must not be null" );
		if ( this instanceof Success < T > ( T data ) ) {
			U mapped = Objects.requireNonNull ( mapper.apply ( data ), "mapper result must not be null" );
			return new Success <> ( mapped );
		} else {
			// Cast to Failure<U> is safe since Failure carries only messages
			return Failure.upcast ( this );
		}
	}

	default < U > OperationResult < U > flatMap ( java.util.function.Function < ? super T, OperationResult < U > > mapper ) {
		Objects.requireNonNull ( mapper, "mapper must not be null" );
		if ( this instanceof Success < T > ( T data ) ) {
			return Objects.requireNonNull ( mapper.apply ( data ), "flatMap mapper must not return null" );
		} else {
			return Failure.upcast ( this );
		}
	}

	default OperationResult < T > filter ( java.util.function.Predicate < ? super T > predicate, String failureMessage ) {
		Objects.requireNonNull ( predicate, "predicate must not be null" );
		Objects.requireNonNull ( failureMessage, "failureMessage must not be null" );
		if ( this instanceof Success < T > ( T data ) ) {
			if ( predicate.test ( data ) ) {
				return this;
			} else {
				return failure ( failureMessage );
			}
		} else {
			return this;
		}
	}

	default T getOrElse ( T defaultValue ) {
		final T value = getOrNull ( );
		return value != null ? value : defaultValue;
	}

	// Supplier-based variant to avoid eager default computation
	default T orElseGet ( java.util.function.Supplier < ? extends T > supplier ) {
		Objects.requireNonNull ( supplier, "supplier must not be null" );
		final T value = getOrNull ( );
		return value != null ? value : supplier.get ( );
	}

	default OperationResult < T > onSuccess ( java.util.function.Consumer < ? super T > consumer ) {
		Objects.requireNonNull ( consumer, "consumer must not be null" );
		if ( this instanceof Success < T > ( T data ) ) {
			consumer.accept ( data );
		}
		return this;
	}

	default OperationResult < T > onFailure ( java.util.function.Consumer < ? super List < String > > consumer ) {
		Objects.requireNonNull ( consumer, "consumer must not be null" );
		if ( this instanceof Failure < T > ( List < String > messages ) ) {
			consumer.accept ( messages );
		}
		return this;
	}

	default < U > U fold ( java.util.function.Function < ? super T, ? extends U > successMapper,
	                       java.util.function.Function < ? super List < String >, ? extends U > failureMapper ) {
		Objects.requireNonNull ( successMapper, "successMapper must not be null" );
		Objects.requireNonNull ( failureMapper, "failureMapper must not be null" );
		if ( this instanceof Success < T > ( T data ) ) {
			return successMapper.apply ( data );
		} else if ( this instanceof Failure < T > ( List < String > messages ) ) {
			return failureMapper.apply ( messages );
		} else {
			throw new IllegalStateException ( "Unknown OperationResult state" );
		}
	}

	default OperationResult < T > recover ( java.util.function.Function < ? super List < String >, ? extends T > recoveryFunction ) {
		Objects.requireNonNull ( recoveryFunction, "recoveryFunction must not be null" );
		if ( this instanceof Failure < T > ( List < String > messages ) ) {
			T recovered = Objects.requireNonNull ( recoveryFunction.apply ( messages ), "recoveryFunction result must not be null" );
			return new Success <> ( recovered );
		} else {
			return this;
		}
	}

	// Success record: immutable carrier of value (can be Void for unit-success)
	record Success < T >( T data ) implements OperationResult < T > {
		// Dedicated unit instance
		private static final Success < Void > UNIT = new Success <> ( null );

		static OperationResult < Void > unit ( ) {
			return UNIT;
		}
	}

	// Failure record: immutable, defensively-copied messages
	record Failure < T >( List < String > messages ) implements OperationResult < T > {
		public Failure {
			Objects.requireNonNull ( messages, "messages must not be null" );
			if ( messages.isEmpty ( ) ) {
				throw new IllegalArgumentException ( "messages must not be empty" );
			}
			if ( messages.stream ( ).anyMatch ( s -> s == null || s.isBlank ( ) ) ) {
				throw new IllegalArgumentException ( "messages must not contain null or blank elements" );
			}
			messages = List.copyOf ( messages );
		}

		public Failure ( String message ) {
			this ( Collections.singletonList ( Objects.requireNonNull ( message, "message must not be null" ) ) );
		}

		// Safe upcast helper to avoid unchecked casts at call sites
		@SuppressWarnings ( "unchecked" )
		static < U > OperationResult < U > upcast ( OperationResult < ? > failure ) {
			return ( OperationResult < U > ) failure;
		}
	}

	// Custom runtime exception to carry failure messages in getOrThrow()
	final class OperationFailedException extends IllegalStateException {
		private final List < String > messages;

		public OperationFailedException ( List < String > messages ) {
			super ( "OperationResult failed with messages: " + messages );
			this.messages = List.copyOf ( messages );
		}

		public List < String > getMessages ( ) { return messages; }
	}
}
