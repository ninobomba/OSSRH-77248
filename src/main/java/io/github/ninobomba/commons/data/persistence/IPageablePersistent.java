package io.github.ninobomba.commons.data.persistence;

import java.util.List;

public interface IPageablePersistent <T>{
	
	List < T > findAll ( );
	
	List < T > findAll ( int offset, int size, String sortBy, String sortOrder );
}
