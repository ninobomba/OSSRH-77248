package io.github.ninobomba.commons.validator.email;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorTest {

	private final EmailValidator validator = new EmailValidator ( );

	@Test
	void isValid_shouldReturnTrue_whenEmailIsValid ( ) {
		assertTrue ( validator.isValid ( "test@example.com", null ) );
	}

	@Test
	void isValid_shouldReturnTrue_whenEmailHasSubdomain ( ) {
		assertTrue ( validator.isValid ( "test@mail.example.com", null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenEmailIsInvalid ( ) {
		assertFalse ( validator.isValid ( "invalid-email", null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenEmailIsNull ( ) {
		assertFalse ( validator.isValid ( null, null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenEmailIsBlank ( ) {
		assertFalse ( validator.isValid ( "", null ) );
	}
}
