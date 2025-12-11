package io.github.ninobomba.commons.validator.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotBlankCharArrayValidatorTest {

	private final NotBlankCharArrayValidator validator = new NotBlankCharArrayValidator ( );

	@Test
	void isValid_shouldReturnTrue_whenArrayHasNonWhitespaceCharacter ( ) {
		assertTrue ( validator.isValid ( new char[] { 'a', ' ' }, null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenArrayIsEmpty ( ) {
		assertFalse ( validator.isValid ( new char[] {}, null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenArrayIsNull ( ) {
		assertFalse ( validator.isValid ( null, null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenArrayContainsOnlyWhitespace ( ) {
		assertFalse ( validator.isValid ( new char[] { ' ', '\t', '\n' }, null ) );
	}
}
