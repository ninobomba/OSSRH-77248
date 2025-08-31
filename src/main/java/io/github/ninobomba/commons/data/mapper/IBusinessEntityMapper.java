package io.github.ninobomba.commons.data.mapper;

import java.util.List;
import java.util.Set;

/**
 * Defines bidirectional mappings between a Business Object (BO) and a persistence ENTITY.
 *
 * @param <BO>     the business object type
 * @param <ENTITY> the entity type
 */
public interface IBusinessEntityMapper < BO, ENTITY > {

	/**
	 * Converts a business object to an entity.
	 *
	 * @param bo the business object to convert
	 * @return the resulting entity
	 */
	ENTITY toEntity ( BO bo );

	/**
	 * Converts a list of business objects to a list of entities.
	 *
	 * @param boList the list of business objects to convert
	 * @return the resulting list of entities
	 */
	List < ENTITY > toEntityList ( List < BO > boList );

	/**
	 * Converts a set of business objects to a set of entities.
	 *
	 * @param boSet the set of business objects to convert
	 * @return the resulting set of entities
	 */
	Set < ENTITY > toEntitySet ( Set < BO > boSet );

	/**
	 * Converts an entity to a business object.
	 *
	 * @param entity the entity to convert
	 * @return the resulting business object
	 */
	BO toBusinessObject ( ENTITY entity );

	/**
	 * Converts a list of entities to a list of business objects.
	 *
	 * @param entityList the list of entities to convert
	 * @return the resulting list of business objects
	 */
	List < BO > toBusinessObjectList ( List < ENTITY > entityList );

	/**
	 * Converts a set of entities to a set of business objects.
	 *
	 * @param entitySet the set of entities to convert
	 * @return the resulting set of business objects
	 */
	Set < BO > toBusinessObjectSet ( Set < ENTITY > entitySet );
}
