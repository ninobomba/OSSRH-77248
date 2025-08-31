package io.github.ninobomba.commons.data.mapper;

import java.util.List;
import java.util.Set;

/**
 * Defines bidirectional mappings between a Business Object (BO) and a Queue payload (QUEUE).
 *
 * @param <BO>    the business object type
 * @param <QUEUE> the queue payload type
 */
public interface IQueueMapper < BO, QUEUE > {

	/**
	 * Converts a business object to a queue payload.
	 *
	 * @param businessObject the business object to convert
	 * @return the resulting queue payload
	 */
	QUEUE toQueue ( BO businessObject );

	/**
	 * Converts a list of business objects to a list of queue payloads.
	 *
	 * @param businessObjectList the list of business objects to convert
	 * @return the resulting list of queue payloads
	 */
	List < QUEUE > toQueueList ( List < BO > businessObjectList );

	/**
	 * Converts a set of business objects to a set of queue payloads.
	 *
	 * @param businessObjectSet the set of business objects to convert
	 * @return the resulting set of queue payloads
	 */
	Set < QUEUE > toQueueSet ( Set < BO > businessObjectSet );

	/**
	 * Converts a queue payload to a business object.
	 *
	 * @param queue the queue payload to convert
	 * @return the resulting business object
	 */
	BO toBusinessObject ( QUEUE queue );

	/**
	 * Converts a list of queue payloads to a list of business objects.
	 *
	 * @param queueList the list of queue payloads to convert
	 * @return the resulting list of business objects
	 */
	List < BO > toBusinessObjectList ( List < QUEUE > queueList );

	/**
	 * Converts a set of queue payloads to a set of business objects.
	 *
	 * @param queueSet the set of queue payloads to convert
	 * @return the resulting set of business objects
	 */
	Set < BO > toBusinessObjectSet ( Set < QUEUE > queueSet );

}
