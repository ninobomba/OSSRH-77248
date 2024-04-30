package io.github.ninobomba.commons.data.persistence;

/**
 * This interface represents a persistent existence check for an entity with the specified ID.
 *
 * @param <ID> the type of the ID associated with the entity
 */
public interface IExistsPersistent < ID > {
	/**
	 * Checks if an entity with the specified ID exists.
	 *
	 * @param id the ID of the entity
	 * @return true if an entity with the specified ID exists, false otherwise
	 */
	boolean existsById ( ID id );
	
}
