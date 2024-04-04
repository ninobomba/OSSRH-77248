package io.github.ninobomba.commons.data.persistence;

public interface IExistsPersistent < ID > {
	boolean existsById ( ID id );
	
}
