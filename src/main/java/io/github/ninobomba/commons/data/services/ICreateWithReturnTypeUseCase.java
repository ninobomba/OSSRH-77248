package io.github.ninobomba.commons.data.services;

public interface ICreateWithReturnTypeUseCase < E, R > {
	
	R create ( E entity );
	
}
