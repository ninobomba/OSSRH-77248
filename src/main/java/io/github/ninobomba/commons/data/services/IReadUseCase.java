package io.github.ninobomba.commons.data.services;

import java.util.List;
import java.util.Optional;

public interface IReadUseCase < T, ID > {
	
	Optional < T > findOne ( T entity );
	
	Optional < T > findById ( ID id );
	
	List < T > findAll ( );

	List < T > findAll ( T entity, int offset, int size );
	
}
