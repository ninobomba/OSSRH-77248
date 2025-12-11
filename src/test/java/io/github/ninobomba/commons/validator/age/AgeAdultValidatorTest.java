package io.github.ninobomba.commons.validator.age;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgeAdultValidatorTest {

	private final AgeAdultValidator validator = new AgeAdultValidator ( );

	@Test
	void isValid_shouldReturnTrue_whenAgeIs18 ( ) {
		assertTrue ( validator.isValid ( LocalDate.now ( ).minusYears ( 18 ), null ) );
	}

	@Test
	void isValid_shouldReturnTrue_whenAgeIsOver18 ( ) {
		assertTrue ( validator.isValid ( LocalDate.now ( ).minusYears ( 19 ), null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenAgeIsUnder18 ( ) {
		assertFalse ( validator.isValid ( LocalDate.now ( ).minusYears ( 17 ), null ) );
	}

	@Test
	void isValid_shouldReturnFalse_whenDateIsNull ( ) {
		assertFalse ( validator.isValid ( null, null ) );
	}

}
