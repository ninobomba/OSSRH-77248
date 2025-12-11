package io.github.ninobomba.commons.validator.array;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankCharArrayValidator implements ConstraintValidator < NotBlankCharArray, char[] > {

	@Override
	public void initialize ( NotBlankCharArray constraintAnnotation ) {
	}

	@Override
	public boolean isValid ( char[] charArray, ConstraintValidatorContext context ) {
		if ( charArray == null || charArray.length == 0 ) {
			return false;
		}

		for ( char c : charArray ) {
			if ( ! Character.isWhitespace ( c ) ) {
				return true;
			}
		}

		return false;
	}
}
