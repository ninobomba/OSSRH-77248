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

	ObjectMapper objectMapper = new ObjectMapper ( )
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

		if ( Objects.isNull ( throwable ) ) return null;

		var response = new StringJoiner ( "\n" );

		var traces = throwable.getStackTrace ( );

		if ( ! Arrays.asList ( traces ).isEmpty ( ) ) {
			response.add ( throwable.getClass ( ) + ": " + throwable.getMessage ( ) );
			for ( StackTraceElement trace : traces )
				response.add ( "    at " + trace.getClassName ( ) + '.' + trace.getMethodName ( ) + '(' + trace.getFileName ( ) + ':' + trace.getLineNumber ( ) + ')' );
		}

		Throwable cause = throwable.getCause ( );
		while ( Objects.nonNull ( cause ) ) {
			StackTraceElement[] causeTraces = cause.getStackTrace ( );
			if ( Objects.nonNull ( causeTraces ) && causeTraces.length > 0 ) {
				response.add ( "Caused By:" );
				response.add ( cause.getClass ( ) + ": " + cause.getMessage ( ) );
				for ( StackTraceElement causeTrace : causeTraces )
					response.add ( "    at " + causeTrace.getClassName ( ) + '.' + causeTrace.getMethodName ( ) + '(' + causeTrace.getFileName ( ) + ':' + causeTrace.getLineNumber ( ) + ')' );
			}
			cause = cause.getCause ( );
		}

		return response.toString ( );
	}
	

	@SneakyThrows
	static String toJsonString ( Map < String, Serializable > type, boolean skipStackTrace ) {
		Map < String, Serializable > filteredMap = null;
		if ( skipStackTrace ) {
			filteredMap = new HashMap <> ( type );
			filteredMap.remove ( "throwable" );
		}
		return objectMapper.writeValueAsString ( filteredMap == null ? type : filteredMap );
	}

}
