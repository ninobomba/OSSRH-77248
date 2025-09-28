package io.github.ninobomba.commons.validator.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator < ValidPassword, String > {

	private static final int MIN_LENGTH = 8;
	private static final int MAX_LENGTH = 20;

	private static final PasswordValidator VALIDATOR = new PasswordValidator (
			List.of (
					new LengthRule ( MIN_LENGTH, MAX_LENGTH ),     // Min 8, Max 20 characters
					new org.passay.RepeatCharactersRule ( ),
					new WhitespaceRule ( )                        // No whitespace
			)
	);

	@Override
	public boolean isValid ( String password, ConstraintValidatorContext context ) {
		// per Bean Validation spec, null should be considered valid; use @NotNull to forbid
		if ( password == null ) {
			return true;
		}

		RuleResult result = VALIDATOR.validate ( new PasswordData ( password ) );
		if ( ! result.isValid ( ) ) {
			context.disableDefaultConstraintViolation ( );
			List < String > messages = VALIDATOR.getMessages ( result );
			String message = String.join ( ", ", messages );
			context.buildConstraintViolationWithTemplate ( message ).addConstraintViolation ( );
			return false;
		}
		return true;
	}
}
