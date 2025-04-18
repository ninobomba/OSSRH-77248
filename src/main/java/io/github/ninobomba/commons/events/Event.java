package io.github.ninobomba.commons.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;
import lombok.Data;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Event class represents an event with a name, unique identifier, timestamp, and elapsed time.
 * It also provides a method to convert the event to a JSON string representation.
 */
@Data
//@Builder( builderClassName = "EventBuilder", buildMethodName = "build" )
public class Event {

	private long id;
	private String name;

	private long elapsedTimeSeconds;
	private long elapsedTimeNanoSeconds;

	private LocalDateTime timestamp;
	private String formattedTimestamp;

	/**
	 * Constructs a new Event object with the given name.
	 *
	 * @param name The name of the event.
	 */
	public Event ( String name ) {
		assignDefaults ( );
		this.name = name;
	}

	/**
	 * This method is used to assign default values to the id, timestamp, and formattedTimestamp variables.
	 * It generates a unique id using the IdGeneratorSnowFlakeSupport.getInstance().getNextId() method.
	 * It sets the current timestamp using the LocalDateTime.now () method.
	 * It formats the timestamp using the DateTimeFormatter.ofPattern() method with the pattern "yyyy-MM-dd HH:mm:ss.SSS".
	 */
	private void assignDefaults ( ) {
		id = IdGeneratorSnowFlakeSupport.getINSTANCE ( ).getNextId ( );
		timestamp = LocalDateTime.now ( );
		formattedTimestamp = timestamp.format ( DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSS" ) );
	}

	/**
	 * This method is used to convert the current object to its JSON string representation.
	 * It utilizes the ObjectMapper class from the Jackson JSON library to perform the serialization.
	 * It registers the JavaTimeModule to enable proper serialization of Java 8 date/time types.
	 * It disables the serialization of dates as timestamps using the SerializationFeature.WRITE_DATES_AS_TIMESTAMPS setting.
	 *
	 * @return The JSON string representation of the current object.
	 */
	@SneakyThrows
	public String toJsonString ( ) {
		var mapper = new ObjectMapper ( );
		mapper.registerModule ( new JavaTimeModule ( ) );
		mapper.disable ( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS );
		return mapper.writeValueAsString ( this );
	}

}
