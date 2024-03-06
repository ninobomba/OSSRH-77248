package io.github.ninobomba.commons.data.generics;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IPersistentReadAction < T extends Serializable, PK extends Serializable > {
	
	Optional < T > findOne ( T entity );
	
	Optional < T > findById ( PK id );
	
	List < T > findAll ( );

	List < T > findAll ( T entity, int offset, int size );
	
}
