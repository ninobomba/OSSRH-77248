package io.github.ninobomba.commons.data.services;

import java.io.Serializable;

public interface IExistsUseCase < ID extends Serializable > {
	boolean existsById ( ID id );
	
}
