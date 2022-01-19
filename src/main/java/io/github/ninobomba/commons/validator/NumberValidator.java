package io.github.ninobomba.commons.validator;


import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NumberValidator implements ConstraintValidator<IsNumber, String>
{
	@Override
	public void initialize(IsNumber constraintAnnotation) {}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext context)
	{
		return NumberUtils.isCreatable( input );
	}
}
