package io.github.ninobomba.commons.data.persistence;

/**
 * This interface defines the contract for creating a persistent object.
 *
 * @param <E> The type of object to be persisted
 */
public interface ICreatePersistent < E > {
	
	/**
	 * Creates a persistent object.
	 *
	 * @param entity the object to be persisted
	 * @return the identifier of the created object
	 */
	Long create( E entity );
	
}
