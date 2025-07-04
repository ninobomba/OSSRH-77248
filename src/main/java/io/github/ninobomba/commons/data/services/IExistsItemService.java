package io.github.ninobomba.commons.data.services;

/**
 * This interface represents a service for checking if an item exists by its ID.
 *
 * @param <ID> the type of the item's ID
 */
public interface IExistsItemService < ID > {
	/**
	 * Determines whether an item with the given ID exists.
	 *
	 * @param id the ID of the item to check
	 * @return true if an item with the given ID exists, otherwise false
	 */
	boolean existsById ( ID id );
	
}
