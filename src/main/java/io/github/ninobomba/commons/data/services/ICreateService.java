package io.github.ninobomba.commons.data.services;

/**
 * This interface represents a service interface for creating entities of type E.
 *
 * @param <E> the type of entity to be created
 */
public interface ICreateService < E > {
	
	/**
	 * Creates a new entity and returns its ID.
	 *
	 * @param entity the entity to be created
	 * @return the ID of the newly created entity
	 */
	Long create( E entity );
	
}
