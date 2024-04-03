package io.github.ninobomba.commons.data.persistence;

import java.io.Serializable;

public interface IDeleteEntityPersistent < E extends Serializable > {
	
	void delete ( E entity );
	
}
