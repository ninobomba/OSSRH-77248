package io.github.ninobomba.commons.data.persistence;

/**
 * The interface IUpdatePersistent represents a contract for updating entities in the database.
 *
 * @param <T> The type of the entity.
 */
public interface IUpdatePersistent < T > {
	/**
	 * Updates the given entity in the database.
	 *
	 * @param entity The entity to be updated.
	 * @param <T>    The type of entity.
	 */
	void update ( T entity );

}
