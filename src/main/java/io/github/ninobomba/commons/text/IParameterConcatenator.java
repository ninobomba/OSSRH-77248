package io.github.ninobomba.commons.text;

import java.util.Optional;

/**
 * The IParameterConcatenator interface provides a method for formatting a processing message by replacing placeholders
 * with actual parameter values.
 */
public interface IParameterConcatenator {
	
	/**
	 * Replaces placeholders in a processing message with actual parameter values.
	 *
	 * @param processingMessage the processing message string with placeholders
	 * @param parameters        the parameter values to replace the placeholders
	 * @return the formatted processing message
	 */
	static String format ( String processingMessage, Object... parameters ) {
		Object[] OBJECT_EMPTY_ARRAY = new Object[] { };
		for ( Object parameter : Optional.ofNullable ( parameters ).orElse ( OBJECT_EMPTY_ARRAY ) )
			processingMessage = processingMessage.replaceFirst ( "\\{}", String.valueOf ( parameter ).replaceAll ( "[^\\dA-Za-z ]", "\\\\$0" ) );
		return processingMessage;
	}
	
}
