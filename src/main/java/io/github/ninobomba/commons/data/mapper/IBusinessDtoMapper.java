package io.github.ninobomba.commons.data.mapper;

import java.util.List;
import java.util.Set;

/**
 * Defines bidirectional mappings between a Business Object (BO) and a Data Transfer Object (DTO).
 *
 * @param <DTO> the data transfer object type
 * @param <BO>  the business object type
 */
public interface IBusinessDtoMapper < DTO, BO > {

	/**
	 * Converts a business object to a DTO.
	 *
	 * @param bo the business object to convert
	 * @return the resulting DTO
	 */
	DTO toDto ( BO bo );

	/**
	 * Converts a list of business objects to a list of DTOs.
	 *
	 * @param boList the list of business objects to convert
	 * @return the resulting list of DTOs
	 */
	List < DTO > toDtoList ( List < BO > boList );

	/**
	 * Converts a set of business objects to a set of DTOs.
	 *
	 * @param boSet the set of business objects to convert
	 * @return the resulting set of DTOs
	 */
	Set < DTO > toDtoSet ( Set < BO > boSet );

	/**
	 * Converts a DTO to a business object.
	 *
	 * @param dto the DTO to convert
	 * @return the resulting business object
	 */
	BO toBusinessObject ( DTO dto );

	/**
	 * Converts a list of DTOs to a list of business objects.
	 *
	 * @param dtoList the list of DTOs to convert
	 * @return the resulting list of business objects
	 */
	List < BO > toBusinessObjectList ( List < DTO > dtoList );

	/**
	 * Converts a set of DTOs to a set of business objects.
	 *
	 * @param dtoSet the set of DTOs to convert
	 * @return the resulting set of business objects
	 */
	Set < BO > toBusinessObjectSet ( Set < DTO > dtoSet );
}
