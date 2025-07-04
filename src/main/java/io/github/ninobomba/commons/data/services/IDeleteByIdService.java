package io.github.ninobomba.commons.data.services;

/**
 * This interface represents a service for deleting an entity by its unique identifier.
 *
 * @param <ID> the data type of the unique identifier
 */
public interface IDeleteByIdService < ID > {
	
	/**
	 * Deletes an entity by its unique identifier.
	 *
	 * @param id the unique identifier of the entity to be deleted
	 */
	void deleteById ( ID id );
	
}
