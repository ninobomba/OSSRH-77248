package io.github.ninobomba.commons.jpa.generics;

import org.openjdk.jmh.util.Optional;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao < T extends Serializable, ID extends Serializable > {
	
	long create ( T newInstance );
	
	void update ( T transientObject );
	
	void delete ( T persistentObject );
	
	Optional < T > findById ( ID id );
	
	List < T > findAll ( );
	
}
