package io.github.ninobomba.commons.data.persistence;

/**
 * This interface represents a contract for creating objects with persistence and returning the created object.
 *
 * @param <E> the type of object to be created
 */
public interface ICreateWithReturnTypePersistent < E > {
	
	/**
	 * Creates an object with persistence and returns the created object.
	 *
	 * @param entity the object to be created
	 * @return the created object
	 */
	E create ( E entity );
	
}
