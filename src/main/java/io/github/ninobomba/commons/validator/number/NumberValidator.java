package io.github.ninobomba.commons.validator.number;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.math.NumberUtils;


/**
 *
 */
public class NumberValidator implements ConstraintValidator < IsNumber, String > {
	@Override
	public void initialize ( IsNumber constraintAnnotation ) {
	}

	@Override
	public boolean isValid ( String input, ConstraintValidatorContext context ) {
		return NumberUtils.isCreatable ( input );
	}

}
