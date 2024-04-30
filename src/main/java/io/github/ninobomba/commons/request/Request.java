package io.github.ninobomba.commons.request;

import io.github.ninobomba.commons.checkpoints.CheckPoint;
import io.github.ninobomba.commons.checkpoints.CheckPointFactory;
import io.github.ninobomba.commons.events.Event;
import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;
import io.github.ninobomba.commons.json.JsonUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.*;

/**
 * The Request class represents a request with a unique ID, name, payload, events, and checkpoints.
 */
@Slf4j
@Builder
public class Request {
	
	@Builder.Default
	private final long id = IdGeneratorSnowFlakeSupport.getInstance ( ).getNextId ( );
	
	@Getter
	private String name;
	@Getter
	private String payload;
	
	@Builder.Default
	private final Queue < Event > eventQueue = new LinkedList <> ( );
	
	@Getter
	private Map < String, CheckPoint > checkPointMap;
	
	/**
	 * Create an instance of checkpoint map
	 *
	 * @param key, the key name based on the filename
	 */
	public void createCheckPointMapWithKey ( String key ) {
		checkPointMap = CheckPointFactory.getInstance ( ).getCheckPointMap ( key );
	}
	
	/**
	 * Adds a new event to the event queue.
	 *
	 * @param name The name of the event to be added.
	 */
	public void pushEvent ( String name ) {
		log.trace ( "Request::pushEvent() _: Adding new event name: {}", name );
		if ( eventQueue.isEmpty ( ) ) eventQueue.add ( new Event ( "__INIT__" ) );
		eventQueue.add ( new Event ( name ) );
	}
	
	/**
	 * Calculates the elapsed time between events in the event queue.
	 * This method updates the elapsed time in each event object.
	 * It uses the timestamps of the events to calculate the elapsed time
	 * in both seconds and nanoseconds. The calculated values are then
	 * set in the respective fields of the event objects.
	 * If the event queue is empty, the method returns without performing any calculations.
	 */
	public void calculateElapsedTime ( ) {
		log.trace ( "Request: calculateElapsedTime() >: start" );
		
		if ( eventQueue.isEmpty ( ) ) return;
		
		var events = eventQueue.toArray ( );
		var length = events.length;
		
		for ( int index = 1 ; index < length ; index++ ) {
			var to = ( Event ) events[ index ];
			var from = ( Event ) events[ index - 1 ];
			
			Duration duration = Duration.between ( from.getTimestamp ( ), to.getTimestamp ( ) );
			to.setElapsedTimeNanoSeconds ( duration.toNanos ( ) );
			to.setElapsedTimeSeconds ( duration.toSeconds ( ) );
			
			log.trace ( "Request: calculateElapsedTime() _: Event => id: {}, name: {}, nano: {}, seconds: {}",
					to.getId ( ),
					to.getName ( ),
					to.getElapsedTimeNanoSeconds ( ),
					to.getElapsedTimeSeconds ( )
			);
		}
		
		log.trace ( "Request: calculateElapsedTime() <: complete" );
	}
	
	/**
	 * Clears the event queue and check point map.
	 */
	public void clear ( ) {
		eventQueue.clear ( );
		checkPointMap.clear ( );
	}
	
	/**
	 * Converts the object to a JSON string representation.
	 *
	 * @param pretty - flag indicating if the JSON string should be formatted with indentation and line breaks
	 * @return the JSON string representation of the object
	 */
	public String toJsonString ( boolean pretty ) {
		calculateElapsedTime ( );
		var events = new StringJoiner ( "," );
		eventQueue.forEach ( e -> events.add ( e.toJsonString ( ) ) );
		
		var checkpoints = new StringJoiner ( "," );
		Optional.ofNullable ( checkPointMap )
				.orElse ( new HashMap <> ( ) )
				.forEach ( ( k, v ) -> checkpoints.add ( v.toJsonString ( ) ) );
		
		var response = "{"
				.concat ( "\"id\":\"" + id + "\"," )
				.concat ( "\"name\":\"" + name + "\"," )
				.concat ( "\"payload\":\"" + payload + "\"," )
				.concat ( "\"events\":[" + events + "]" + "," )
				.concat ( "\"checkpoints\":[" + checkpoints + "]" )
				.concat ( "}" );
		
		return pretty ? JsonUtils.pretty ( response ) : response;
	}
	
}
