package io.github.ninobomba.commons.data.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

/**
 * The IEntityMapper interface defines methods for mapping between DTO and ENTITY objects.
 * Implementations of this interface handle the conversion between DTOs and ENTITY objects in a consistent manner.
 *
 * @param <DTO>    The type representing the DTO object
 * @param <ENTITY> The type representing the ENTITY object
 */
public interface IEntityMapper < DTO, ENTITY > {

	/**
	 * Converts an ENTITY object to a DTO object.
	 *
	 * @param entity the ENTITY object to be converted
	 * @return the resulting DTO object
	 */
	DTO toDTO ( ENTITY entity );

	/**
	 * Converts a DTO object to an ENTITY object.
	 *
	 * @param dto the DTO object to be converted
	 * @return the resulting ENTITY object
	 */
	ENTITY toEntity ( DTO dto );

	/**
	 * Updates an existing entity object with the values from a DTO object.
	 *
	 * @param dto    the DTO object containing the updated values
	 * @param entity the ENTITY object to be updated with the values from the DTO
	 * @return the updated ENTITY object
	 */
	ENTITY toEntity ( DTO dto, @MappingTarget ENTITY entity );

	/**
	 * Converts a list of DTO objects to a list of ENTITY objects.
	 *
	 * @param dtoList the list of DTO objects to be converted
	 * @return the resulting list of ENTITY objects
	 */
	List < ENTITY > toEntityList ( List < DTO > dtoList );

	/**
	 * Converts a list of ENTITY objects to a list of DTO objects.
	 *
	 * @param entityList the list of ENTITY objects to be converted
	 * @return the resulting list of DTO objects
	 */
	List < DTO > toDTOList ( List < ENTITY > entityList );

	/**
	 * Converts a set of ENTITY objects to a set of DTO objects.
	 *
	 * @param entitySet the set of ENTITY objects to be converted
	 * @return the resulting set of DTO objects
	 */
	Set < DTO > toDTOSet ( Set < ENTITY > entitySet );

	/**
	 * Converts a set of DTO objects to a set of ENTITY objects.
	 *
	 * @param dtoSet the set of DTO objects to be converted
	 * @return the resulting set of ENTITY objects
	 */
	Set < ENTITY > toEntitySet ( Set < DTO > dtoSet );

}
