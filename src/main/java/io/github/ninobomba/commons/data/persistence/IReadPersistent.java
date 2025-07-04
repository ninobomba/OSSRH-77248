package io.github.ninobomba.commons.data.persistence;

import java.util.Optional;

/**
 * The IReadPersistent interface defines the contract for reading entities from a persistent storage.
 * Implementing classes must provide implementations for the methods declared in this interface.
 *
 * @param <T> The type of entity being read from the persistent storage
 * @param <PK> The type of the primary key used to identify the entities
 */
public interface IReadPersistent < T, PK > {
	
	/**
	 * Retrieves an entity from the database based on the provided entity.
	 *
	 * @param entity the entity used to query the database
	 * @return an Optional containing the retrieved entity, or an empty Optional if no entity is found
	 */
	Optional < T > findOne ( T entity );
	
	/**
	 * Retrieves an entity from the database based on the provided id.
	 *
	 * @param id the id used to query the database
	 * @return an Optional containing the retrieved entity, or an empty Optional if no entity is found
	 */
	Optional < T > findById ( PK id );
	
}
