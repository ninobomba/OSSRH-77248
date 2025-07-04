package io.github.ninobomba.commons.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

/**
 * The JsonUtils interface provides utility methods for working with JSON data.
 */
public interface JsonUtils {

	ObjectMapper objectMapper = new ObjectMapper ( )
			.configure ( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false )
			.enable ( SerializationFeature.INDENT_OUTPUT );

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
	static String format ( String json ) {
		return objectMapper
				.writerWithDefaultPrettyPrinter ( )
				.writeValueAsString ( objectMapper.readValue ( json, Object.class ) );
	}

	/**
	 * Formats a string containing JSON data into a more readable and indented format.
	 *
	 * @param json         The string containing the JSON data to be formatted.
	 * @param typeInstance An instance of the type to which the JSON should be converted.
	 * @param <T>          The type of the object to which the JSON should be converted.
	 * @return The formatted JSON string.
	 * @apiNote This method uses the ObjectMapper class to convert the given JSON string to an object of the provided type,
	 * and then converts the object back to a JSON string with the pretty printer enabled for indentation.
	 * @implSpec This method assumes that the JSON string is valid and can be deserialized into the provided type.
	 */
	@SneakyThrows
	static < T > String format ( String json, T typeInstance ) {
		return objectMapper
				.writerWithDefaultPrettyPrinter ( )
				.writeValueAsString ( objectMapper.readValue ( json, typeInstance.getClass ( ) ) );
	}

	/**
	 * Converts a JSON string to a formatted string representation.
	 *
	 * @param json The JSON string to be converted.
	 * @return The formatted string representation of the JSON.
	 */
	@SneakyThrows
	static String convertJsonToString ( String json ) {
		return objectMapper
				.writer ( )
				.writeValueAsString ( objectMapper.readValue ( json, Object.class ) );
	}

	/**
	 * Converts a JSON string to a string representation using the provided type instance.
	 *
	 * @param json         The JSON string to be converted.
	 * @param typeInstance An instance of the type to which the JSON should be converted.
	 * @param <T>          The type of the object to which the JSON should be converted.
	 * @return The string representation of the JSON.
	 */
	@SneakyThrows
	static < T > String convertJsonToString ( String json, T typeInstance ) {
		return objectMapper
				.writer ( )
				.writeValueAsString ( objectMapper.readValue ( json, typeInstance.getClass ( ) ) );
	}

}
