package io.github.ninobomba.commons.data.services;

public interface ICRUDService < T, ID > {

	/**
	 * Saves the given entity.
	 *
	 * @param entity the entity to save
	 * @return the saved entity
	 */
	T create ( T entity );


	/**
	 * Updates the given entity.
	 *
	 * @param entity the entity to update
	 * @return the updated entity
	 */
	T update ( T entity );


	/**
	 * Reads and retrieves the given entity.
	 *
	 * @param entity the entity to be read
	 * @return the retrieved entity
	 */
	T read ( T entity );


	/**
	 * Deletes the given entity.
	 *
	 * @param entity the entity to delete
	 */
	void delete ( T entity );

	/**
	 * Deletes the entity with the given ID.
	 *
	 * @param id the ID of the entity to delete
	 */
	void deleteById ( ID id );

}
