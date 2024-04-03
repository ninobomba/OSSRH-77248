package io.github.ninobomba.commons.data.services;

public interface ICreateUseCase < E > {
	void create( E entity, String... parameters );
	
}
