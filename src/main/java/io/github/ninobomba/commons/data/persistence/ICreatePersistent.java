package io.github.ninobomba.commons.data.persistence;

public interface ICreatePersistent < E > {
	
	Long create( E entity );
	
}
