package io.github.ninobomba.commons.data.persistence;

public interface ICreateWithReturnTypePersistent < E, R > {
	
	R create ( E entity );
	
}
