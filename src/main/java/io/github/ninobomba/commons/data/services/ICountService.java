package io.github.ninobomba.commons.data.services;

/**
 * The ICountService interface provides a method for counting elements of a specific type.
 *
 * @param <E> the type of elements to be counted
 */
public interface ICountService < E > {

	/**
	 * Counts the number of elements of a specific type.
	 *
	 * @param type the type of elements to be counted
	 * @return the number of elements of the specified type
	 */
	int count( E type );
	
}
