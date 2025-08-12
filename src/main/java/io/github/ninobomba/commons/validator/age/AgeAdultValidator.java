package io.github.ninobomba.commons.validator.age;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeAdultValidator implements ConstraintValidator < AgeAdult, LocalDate > {

	@Override
	public boolean isValid ( LocalDate dateOfBirth, ConstraintValidatorContext context ) {
		if ( dateOfBirth == null ) {
			return false; // Or false, depending on if null is valid or not
		}
		return Period.between ( dateOfBirth, LocalDate.now ( ) ).getYears ( ) >= 18;
	}
}
