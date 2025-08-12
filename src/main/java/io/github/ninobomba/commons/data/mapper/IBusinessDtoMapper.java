package io.github.ninobomba.commons.data.mapper;

import java.util.List;
import java.util.Set;

public interface IBusinessDtoMapper < D, B > {

	D toDto ( B b );

	List < D > toDtoList ( List < B > list );

	Set < D > toDtoSet ( Set < B > set );


	B toBusinessObject ( D d );

	List < B > toBusinessObjectList ( List < D > list );

	Set < B > toBusinessObjectSet ( Set < D > set );

}
