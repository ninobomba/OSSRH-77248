package io.github.ninobomba.commons.data.persistence;

/**
 * This interface defines the contract for classes that implement
 * the ability to delete objects by their unique identifier.
 *
 * @param <ID> the type of the identifier
 */
public interface IDeletePersistent < ID > {
	
	/**
	 * Deletes an object by its unique identifier.
	 *
	 * @param id the unique identifier of the object to be deleted
	 */
	void deleteById ( ID id );
	
}
