package io.github.ninobomba.commons.data.persistence;

public interface ICreatePersistent < E > {
	void create( E entity, String... parameters );
	
}
