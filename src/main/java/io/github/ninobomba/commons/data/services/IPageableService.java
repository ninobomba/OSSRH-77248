package io.github.ninobomba.commons.data.services;


import java.util.List;

/**
 * The IPageableService interface provides methods for retrieving paginated results from a data source.
 *
 * @param <T> the type of objects handled by this service
 */
public interface IPageableService < T > {
	
	/**
	 * Retrieves all the elements from the data source.
	 *
	 * @return a list of elements of type T
	 */
	List < T > findAll ( );
	
	/**
	 * Retrieves a paginated list of elements from the data source.
	 *
	 * @param offset     the starting index of the result set (0-based)
	 * @param size       the maximum number of elements to retrieve
	 * @param sortBy     the field to sort the elements by
	 * @param sortOrder  the order in which to sort the elements ("asc" for ascending, "desc" for descending)
	 *
	 * @return a paginated list of elements of type T
	 */
	List < T > findAll ( int offset, int size, String sortBy, String sortOrder );

	
}
