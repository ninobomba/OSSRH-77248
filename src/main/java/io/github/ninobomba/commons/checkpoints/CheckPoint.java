package io.github.ninobomba.commons.checkpoints;

import lombok.Data;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CheckPoint implements Cloneable {
	
	private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss.SSS" );
	
	private String id;
	private String module;
	private String name;
	private String description;
	private int order;
	
	private Boolean completed;
	
	private LocalDateTime localDateTime;
	private String formattedTimestamp;
	
	/**
	 * Updates the current state of the object.
	 * Retrieves the current timestamp, formats it with the specified pattern,
	 * and marks the operation as completed.
	 */
	public void update ( ) {
		localDateTime = LocalDateTime.now ( );
		formattedTimestamp = localDateTime.format ( pattern );
		completed = true;
	}
	
	/**
	 * Creates and returns a copy of the current object.
	 *
	 * @return a clone of the object
	 * @throws CloneNotSupportedException if the object's class does not implement the Cloneable interface
	 */
	public Object clone ( ) throws CloneNotSupportedException {
		return super.clone ( );
	}
	
	@SneakyThrows
	public String toJsonString ( ) {
		return "{"
				.concat ( "\"id\":" + "\"" + id + "\"" + "," )
				.concat ( "\"module\":" + "\"" + module + "\"" + "," )
				.concat ( "\"name\":" + "\"" + name + "\"" + "," )
				.concat ( "\"order\":" + "\"" + order + "\"" + "," )
				.concat ( "\"completed\":" + "\"" + completed + "\"" + "," )
				.concat ( "\"localDateTime\":" + "\"" + localDateTime + "\"" + "," )
				.concat ( "\"formattedTimestamp\":" + "\"" + formattedTimestamp + "\"" )
				.concat ( "}" );
	}
	
}
