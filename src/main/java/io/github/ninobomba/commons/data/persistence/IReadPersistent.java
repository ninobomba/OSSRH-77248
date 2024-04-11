package io.github.ninobomba.commons.data.persistence;

import java.util.Optional;

public interface IReadPersistent < T, PK > {
	
	Optional < T > findOne ( T entity );
	
	Optional < T > findById ( PK id );
	
}
