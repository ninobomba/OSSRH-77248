package io.github.ninobomba.commons.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

/**
 * The JsonUtils interface provides utility methods for working with JSON data.
 */
public interface JsonUtils {

	ObjectMapper objectMapper = new ObjectMapper ( ).enable ( SerializationFeature.INDENT_OUTPUT );

	/**
	 * Checks if a given string is a valid JSON.
	 *
	 * @param json The string to be checked.
	 * @return True if the string is a valid JSON, false otherwise.
	 */
	static boolean isValidJson ( String json ) {
		try {
			objectMapper.readTree ( json );
		} catch ( JsonProcessingException e ) {
			return false;
		}
		return true;
	}

	/**
	 * Formats a string containing JSON data into a more readable and indented format.
	 *
	 * @param json The string containing the JSON data to be formatted.
	 * @return The formatted JSON string.
	 */
	@SneakyThrows
	static String pretty ( String json ) {
		return objectMapper
				.writerWithDefaultPrettyPrinter ( )
				.writeValueAsString ( objectMapper.readValue ( json, Object.class ) );
	}

}
