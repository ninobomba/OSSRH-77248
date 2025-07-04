package io.github.ninobomba.commons.data.services;

/**
 * The ICreateWithReturnTypeService interface defines a contract for creating entities and returning the created entity.
 *
 * @param <E> the type of the entity
 */
public interface ICreateWithReturnTypeService < E > {
	
	/**
	 * Creates a new entity.
	 *
	 * @param entity the entity to be created
	 * @return the created entity
	 */
	E create ( E entity );
	
}
