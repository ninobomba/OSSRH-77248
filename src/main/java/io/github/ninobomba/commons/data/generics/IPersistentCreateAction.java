package io.github.ninobomba.commons.data.generics;

public interface IPersistentCreateAction < E > {
	void create( E entity, String... parameters );
	
}
