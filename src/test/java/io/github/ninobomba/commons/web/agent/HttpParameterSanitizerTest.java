package io.github.ninobomba.commons.web.agent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpParameterSanitizerTest {

	@Test
	void sanitize_shouldRemoveSpecialCharacters ( ) {
		String input = "Hello@# World$%";
		String expected = "Hello World";
		assertEquals ( expected, HttpParameterSanitizer.sanitize ( input ) );
	}

	@Test
	void sanitize_shouldReturnDefaultMessage_whenInputIsNull ( ) {
		assertEquals ( "No value provided", HttpParameterSanitizer.sanitize ( null ) );
	}

	@Test
	void sanitize_shouldKeepAlphanumericAndSpaces ( ) {
		String input = "User 123";
		assertEquals ( "User 123", HttpParameterSanitizer.sanitize ( input ) );
	}
}
