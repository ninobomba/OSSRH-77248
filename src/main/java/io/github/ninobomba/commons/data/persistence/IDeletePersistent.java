package io.github.ninobomba.commons.data.persistence;

import java.io.Serializable;

public interface IDeletePersistent < ID extends Serializable > {
	
	void deleteById ( ID id );
	
}
