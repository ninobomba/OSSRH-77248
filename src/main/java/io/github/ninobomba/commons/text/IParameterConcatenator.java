package io.github.ninobomba.commons.text;

import java.util.Optional;

public interface IParameterConcatenator {
	
	static String format ( String processingMessage, Object... parameters ) {
		Object[] OBJECT_EMPTY_ARRAY = new Object[] { };
		for ( Object parameter : Optional.ofNullable ( parameters ).orElse ( OBJECT_EMPTY_ARRAY ) )
			processingMessage = processingMessage.replaceFirst ( "\\{}", String.valueOf ( parameter ).replaceAll ( "[^\\dA-Za-z ]", "\\\\$0" ) );
		return processingMessage;
	}
	
}
