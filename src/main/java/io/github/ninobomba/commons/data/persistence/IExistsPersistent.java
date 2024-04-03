package io.github.ninobomba.commons.data.persistence;

import java.io.Serializable;

public interface IExistsPersistent < ID extends Serializable > {
	boolean existsById ( ID id );
	
}
