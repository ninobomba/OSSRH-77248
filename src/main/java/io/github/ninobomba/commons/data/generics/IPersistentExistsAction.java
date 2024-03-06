package io.github.ninobomba.commons.data.generics;

import java.io.Serializable;

public interface IPersistentExistsAction < ID extends Serializable > {
	boolean existsById ( ID id );
	
}
