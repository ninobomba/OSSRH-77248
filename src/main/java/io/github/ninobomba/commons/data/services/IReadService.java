package io.github.ninobomba.commons.data.services;

import java.util.Optional;

/**
 * The interface IReadService is a generic interface for reading operations.
 *
 * @param <T>  the entity type
 * @param <ID> the ID type
 */
public interface IReadService < T, ID > {
	
	/**
	 * Finds and returns an optional entity that matches the given entity.
	 *
	 * @param entity the entity to find
	 * @param <T>    the entity type
	 * @return an optional entity that matches the given entity, or an empty optional if no match is found
	 */
	Optional < T > findOne ( T entity );
	
	/**
	 * Finds and returns an Optional entity based on the provided ID.
	 *
	 * @param id the ID of the entity to find
	 * @return an Optional entity that matches the given ID, or an empty Optional if no match is found
	 */
	Optional < T > findById ( ID id );
	
}
