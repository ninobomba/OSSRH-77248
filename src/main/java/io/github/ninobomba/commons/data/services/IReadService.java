package io.github.ninobomba.commons.data.services;

import java.util.Optional;

public interface IReadService < T, ID > {
	
	Optional < T > findOne ( T entity );
	
	Optional < T > findById ( ID id );
	
}
