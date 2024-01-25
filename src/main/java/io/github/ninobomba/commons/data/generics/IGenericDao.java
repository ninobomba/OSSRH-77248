package io.github.ninobomba.commons.data.generics;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IGenericDao < T extends Serializable, PK extends Serializable > {
	
	long create ( T newInstance );
	
	void update ( T transientObject );
	
	void delete ( T persistentObject );
	
	Optional < T > findById ( PK id );
	
	List < T > findAll ( );
	
}
