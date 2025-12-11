package io.github.ninobomba.commons.validator.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberValidatorTest {

	private final NumberValidator validator = new NumberValidator ( );

	@Test
	void isValid_shouldReturnTrue_whenInputIsInteger ( ) {
		assertTrue ( validator.isValid ( "123", null ) );
	}

	@Test
	void isValid_shouldReturnTrue_whenInputIsDouble ( ) {
		assertTrue ( validator.isValid ( "123.45", null ) );
	}

	@Test
	void isValid_shouldReturnTrue_whenInputIsNegative ( ) {
		assertTrue ( validator.isValid ( "-123", null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenInputIsNotNumber ( ) {
		assertFalse ( validator.isValid ( "abc", null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenInputIsNull ( ) {
		assertFalse ( validator.isValid ( null, null ) );
	}
}
