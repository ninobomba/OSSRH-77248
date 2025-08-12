package io.github.ninobomba.commons.data.mapper;

import java.util.List;
import java.util.Set;

public interface IBusinessEntityMapper < B, E > {

	E toEntity ( B BusinessObject );

	List < E > toEntityList ( List < B > BusinessObjectList );

	Set < E > toEntitySet ( Set < B > BusinessObjectSet );


	B toBusinessObject ( E entity );

	List < B > toBusinessObjectList ( List < E > list );

	Set < B > toBusinessObjectSet ( Set < E > set );


}
