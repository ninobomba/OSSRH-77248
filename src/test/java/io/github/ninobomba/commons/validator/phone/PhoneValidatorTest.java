package io.github.ninobomba.commons.validator.phone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneValidatorTest {

	private final PhoneValidator validator = new PhoneValidator ( );

	@Test
	void isValid_shouldReturnTrue_whenPhoneIs10Digits ( ) {
		assertTrue ( validator.isValid ( "1234567890", null ) );
	}

	@Test
	void isValid_shouldReturnTrue_whenPhoneIsFormattedWithDashes ( ) {
		assertTrue ( validator.isValid ( "123-456-7890", null ) );
	}

	@Test
	void isValid_shouldReturnTrue_whenPhoneIsFormattedWithParenthesesAndDashes ( ) {
		assertTrue ( validator.isValid ( "(123) 456-7890", null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenPhoneIsInvalid ( ) {
		assertFalse ( validator.isValid ( "123", null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenPhoneIsNull ( ) {
		assertFalse ( validator.isValid ( null, null ) );
	}
}
