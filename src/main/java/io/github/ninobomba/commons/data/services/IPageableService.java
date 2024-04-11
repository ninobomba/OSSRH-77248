package io.github.ninobomba.commons.data.services;


import java.util.List;

public interface IPageableService < T > {
	
	List < T > findAll ( );
	
	List < T > findAll ( int offset, int size, String sortBy, String sortOrder );

	
}
