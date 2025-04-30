package io.github.ninobomba.commons.web.agent;

import java.util.Optional;

public class HttpParameterSanitizer {

	public static String sanitize ( String input ) {
		return Optional.ofNullable ( input )
				.map ( value -> value.replaceAll ( "[^a-zA-Z0-9\\s]" , "" ) )
				.orElse ( "No value provided" );
	}

}
