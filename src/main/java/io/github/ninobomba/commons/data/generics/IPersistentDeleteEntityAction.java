package io.github.ninobomba.commons.data.generics;

import java.io.Serializable;

public interface IPersistentDeleteEntityAction < E extends Serializable > {
	
	void delete ( E entity );
	
}
