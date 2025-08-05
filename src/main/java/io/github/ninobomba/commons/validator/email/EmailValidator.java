package io.github.ninobomba.commons.validator.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
 * The EmailValidator class is responsible for validating email addresses based on a regular expression.
 * It implements the ConstraintValidator interface and provides an isValid method to perform the validation.
 *
 * <p>The regular expression used for validation ensures that the email address follows the standard email format.
 *
 * <p>Example usage:
 * <pre>
 *     EmailValidator emailValidator = new EmailValidator();
 *     boolean isValid = emailValidator.isValid("test@example.com", null);
 * </pre>
 */
public class EmailValidator implements ConstraintValidator < Email, String > {

	private static final String REGEX =
			"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

	@Override
	public boolean isValid ( String value, ConstraintValidatorContext context ) {
		if ( StringUtils.isBlank ( value ) ) return false;
		return value.matches ( REGEX );
	}

}
