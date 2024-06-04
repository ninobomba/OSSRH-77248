package io.github.ninobomba.commons.data.persistence;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a pageable persistent class.
 */
public interface IPageablePersistent < T > {

	/**
	 * Returns a list of all elements of type T.
	 *
	 * @return A list containing all elements of type T
	 */
	Optional < List < T > > findAll ( );

	/**
	 * Finds a list of elements of type T based on the provided pagination parameters.
	 *
	 * @param offset    the index of the first element to include in the result set
	 * @param size      the maximum number of elements to return
	 * @param sortBy    the field to sort the results by
	 * @param sortOrder the order in which to sort the results (ascending or descending)
	 * @return a list of elements of type T that match the pagination parameters
	 */
	Optional < List < T > > findAll ( int offset, int size, String sortBy, String sortOrder );
}
