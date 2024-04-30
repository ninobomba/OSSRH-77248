package io.github.ninobomba.commons.data.persistence;

/**
 * This interface represents a contract for classes that provide the functionality to delete an entity from persistent storage.
 *
 * @param <E> the type of the entity to be deleted
 */
public interface IDeleteEntityPersistent < E > {
	
	/**
	 * Deletes an entity from persistent storage.
	 *
	 * @param entity the entity to be deleted
	 */
	void delete ( E entity );
	
}
