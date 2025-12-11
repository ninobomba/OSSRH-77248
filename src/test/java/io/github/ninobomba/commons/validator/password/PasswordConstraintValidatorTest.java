package io.github.ninobomba.commons.validator.password;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith ( MockitoExtension.class )
class PasswordConstraintValidatorTest {

	private final PasswordConstraintValidator validator = new PasswordConstraintValidator ( );

	@Mock
	private ConstraintValidatorContext context;

	@Mock
	private ConstraintValidatorContext.ConstraintViolationBuilder builder;

	@Test
	void isValid_shouldReturnTrue_whenPasswordIsValid ( ) {
		assertTrue ( validator.isValid ( "ValidPassword123", context ) );
	}

	@Test
	void isValid_shouldReturnTrue_whenPasswordIsNull ( ) {
		assertTrue ( validator.isValid ( null, context ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenPasswordIsTooShort ( ) {
		// Mock context behavior for invalid result
		when ( context.buildConstraintViolationWithTemplate ( anyString ( ) ) ).thenReturn ( builder );
		when ( builder.addConstraintViolation ( ) ).thenReturn ( context );

		assertFalse ( validator.isValid ( "short", context ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenPasswordContainsWhitespace ( ) {
		// Mock context behavior for invalid result
		when ( context.buildConstraintViolationWithTemplate ( anyString ( ) ) ).thenReturn ( builder );
		when ( builder.addConstraintViolation ( ) ).thenReturn ( context );

		assertFalse ( validator.isValid ( "pass word", context ) );
	}
}
