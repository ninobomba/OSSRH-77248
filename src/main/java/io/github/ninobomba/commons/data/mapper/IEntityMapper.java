package io.github.ninobomba.commons.data.mapper;

import java.util.List;
import java.util.Set;

public interface IEntityMapper < DTO , ENTITY > {
	
	DTO toDTO ( ENTITY entity );
	
	ENTITY toEntity ( DTO dto );
	
	List < ENTITY > toEntityList ( List < DTO > dtoList );
	
	List < DTO > toDTOList ( List < ENTITY > entityList );
	
	Set < DTO > toDTOSet ( Set < ENTITY > entitySet );
	
	Set < ENTITY > toEntitySet ( Set < DTO > dtoSet );
	
}
