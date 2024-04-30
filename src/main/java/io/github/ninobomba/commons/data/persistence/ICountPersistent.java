package io.github.ninobomba.commons.data.persistence;

/**
 * The ICountPersistent interface provides a method for counting the occurrences of a given type.
 *
 * @param <E> the type of element to count
 */
public interface ICountPersistent < E > {

	/**
	 * Counts the occurrences of a given type.
	 *
	 * @param type the type of element to count
	 * @return the number of occurrences of the given type
	 */
	int count( E type );
	
}
