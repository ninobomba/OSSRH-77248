package io.github.ninobomba.commons.data.persistence;

import java.util.List;
import java.util.Optional;

public interface IReadPersistent < T, PK > {
	
	Optional < T > findOne ( T entity );
	
	Optional < T > findById ( PK id );
	
	List < T > findAll ( );

	List < T > findAll ( T entity, int offset, int size );
	
}
