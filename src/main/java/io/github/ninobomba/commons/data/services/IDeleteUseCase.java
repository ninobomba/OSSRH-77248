package io.github.ninobomba.commons.data.services;

import java.io.Serializable;

public interface IDeleteUseCase < ID extends Serializable > {
	
	void deleteById ( ID id );
	
}
