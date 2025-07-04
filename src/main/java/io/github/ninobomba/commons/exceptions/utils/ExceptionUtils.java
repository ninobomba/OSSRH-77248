package io.github.ninobomba.commons.exceptions.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.*;

/**
 * The ExceptionUtils interface provides utility methods for working with exceptions.
 */
public interface ExceptionUtils {
	ObjectMapper OBJECT_MAPPER = new ObjectMapper ( )
			.configure ( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false )
			.enable ( SerializationFeature.INDENT_OUTPUT );


	/**
	 * Converts a Throwable object to a String representation.
	 *
	 * @param throwable the Throwable object to convert
	 * @return the String representation of the Throwable object
	 */
	@SneakyThrows
	static String convertToString ( Throwable throwable ) {
		if ( Objects.isNull ( throwable ) ) {
			return null;
		}

		StringJoiner response = new StringJoiner ( "\n" );
		addStackTrace ( response, throwable );

		String CAUSED_BY = "Caused By:";

		Throwable cause = throwable.getCause ( );
		while ( Objects.nonNull ( cause ) ) {
			response.add ( CAUSED_BY );
			addStackTrace ( response, cause );
			cause = cause.getCause ( );
		}
		return response.toString ( );
	}

	private static void addStackTrace ( StringJoiner response, Throwable throwable ) {
		String INDENTATION = "    ";
		response.add ( throwable.getClass ( ) + ": " + throwable.getMessage ( ) );
		Arrays.stream ( throwable.getStackTrace ( ) )
				.forEach ( trace -> response.add ( String.format ( "%sat %s.%s(%s:%d)",
						INDENTATION,
						trace.getClassName ( ),
						trace.getMethodName ( ),
						trace.getFileName ( ),
						trace.getLineNumber ( ) ) ) );
	}

	/**
	 * Converts a map to its JSON string representation.
	 *
	 * @param map            The map to be converted
	 * @param skipStackTrace Flag indicating whether to skip the stack trace
	 * @return JSON string representation of the map
	 */
	@SneakyThrows
	static String toJsonString ( Map < String, Serializable > map, boolean skipStackTrace ) {
		Map < String, Serializable > result = skipStackTrace ? new HashMap <> ( map ) : map;
		if ( skipStackTrace )
			result.remove ( "throwable" );
		return OBJECT_MAPPER.writeValueAsString ( result );
	}
}
