package io.github.ninobomba.commons.patterns.lazy;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents a lazily evaluated value. The value is computed only when it is needed,
 * and once computed, it is stored and returned on subsequent calls.
 *
 * @param <T> The type of the value being lazily evaluated.
 */
public final class Lazy < T > implements Supplier < T > {
	private transient Supplier < T > supplier;
	private volatile T value;

	/**
	 * Constructs a Lazy object with the given supplier.
	 *
	 * @param supplier the supplier to use for lazy initialization of the value
	 */
	public Lazy ( Supplier < T > supplier ) {
		this.supplier = Objects.requireNonNull ( supplier );
	}

	/**
	 * Retrieves the lazily evaluated value. The value is computed only when it is needed,
	 * and once computed, it is stored and returned on subsequent calls.
	 *
	 * @return The lazily evaluated value.
	 */
	@Override
	public T get ( ) {
		if ( value == null ) {
			synchronized ( this ) {
				if ( value == null ) {
					value = Objects.requireNonNull ( supplier.get ( ) );
					supplier = null;
				}
			}
		}
		return value;
	}

	/**
	 * Applies the given mapper function to the value of this Lazy object and returns a new Lazy object with the mapped value.
	 *
	 * @param <R>    the type of the mapped value
	 * @param mapper the function to apply to the value
	 * @return a new Lazy object with the mapped value
	 */
	public < R > Lazy < R > map ( Function < T, R > mapper ) {
		return new Lazy <> ( ( ) -> mapper.apply ( this.get ( ) ) );
	}

	/**
	 * Applies a given mapper function to the value inside the Lazy object,
	 * and returns a new Lazy object with the result of the mapping function.
	 *
	 * @param mapper The function to apply to the value inside the Lazy object.
	 *               It accepts a value of type T and returns a Lazy object
	 *               that holds a value of type R.
	 * @param <R>    The type of the value inside the Lazy object returned by the mapper function.
	 * @return A new Lazy object with the result of the mapping function.
	 */
	public < R > Lazy < R > flatMap ( Function < T, Lazy < R > > mapper ) {
		return new Lazy <> ( ( ) -> mapper.apply ( this.get ( ) ).get ( ) );
	}

	/**
	 * Filters the value of the Lazy Optional using the specified predicate.
	 * The Lazy Optional is returned which contains the result of applying
	 * the predicate on the value of the original Lazy Optional.
	 *
	 * @param predicate the predicate to apply on the value of the Lazy Optional
	 * @return the Lazy Optional containing the result of applying the predicate
	 */
	public Lazy < Optional < T > > filter ( Predicate < T > predicate ) {
		return new Lazy <> ( ( ) -> Optional.of ( get ( ) ).filter ( predicate ) );
	}

	/**
	 * Creates a lazy wrapper around the given supplier.
	 * The supplier is used to lazily initialize the value contained in the wrapper.
	 *
	 * @param supplier the supplier used to initialize the value
	 * @param <T>      the type of the value
	 * @return a new Lazy instance with the specified supplier
	 */
	public static < T > Lazy < T > of ( Supplier < T > supplier ) {
		return new Lazy <> ( supplier );
	}

}
