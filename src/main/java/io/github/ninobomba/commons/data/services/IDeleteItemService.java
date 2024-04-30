package io.github.ninobomba.commons.data.services;

/**
 * This interface represents a service for deleting an item.
 *
 * @param <E> the type of the item to be deleted
 */
public interface IDeleteItemService < E > {
	
	/**
	 * Deletes the specified entity.
	 *
	 * @param entity the entity to be deleted
	 */
	void delete ( E entity );
	
}
