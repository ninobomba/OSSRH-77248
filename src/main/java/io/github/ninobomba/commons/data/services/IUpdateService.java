package io.github.ninobomba.commons.data.services;

/**
 * The IUpdateService interface provides a contract for implementing
 * update functionality for a specific entity.
 *
 * @param <T> The type of entity to be updated.
 */
public interface IUpdateService < T > {
	
	/**
	 * Updates the specified entity.
	 *
	 * @param entity The entity to be updated. It should be of type T.
	 */
	void update ( T entity );

}
