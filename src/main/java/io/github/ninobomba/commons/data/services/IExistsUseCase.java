package io.github.ninobomba.commons.data.services;

public interface IExistsUseCase < ID > {
	boolean existsById ( ID id );
	
}
