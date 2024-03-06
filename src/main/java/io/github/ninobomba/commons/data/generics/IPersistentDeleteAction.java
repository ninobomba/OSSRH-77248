package io.github.ninobomba.commons.data.generics;

import java.io.Serializable;

public interface IPersistentDeleteAction < ID extends Serializable > {
	
	void deleteById ( ID id );
	
}
