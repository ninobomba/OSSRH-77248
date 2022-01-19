package io.github.ninobomba.commons.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String>
{

	private static final String REGEX =
			"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context)
	{
		if(StringUtils.isBlank( value) ) return false;
		return value.matches( REGEX );
	}

}
