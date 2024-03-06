package io.github.ninobomba.commons.data.generics;

public interface IPersistentCreateWithReturnTypeAction< E, R > {
	
	R create ( E entity );
	
}
