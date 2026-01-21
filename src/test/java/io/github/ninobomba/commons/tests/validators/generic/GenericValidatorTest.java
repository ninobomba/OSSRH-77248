package io.github.ninobomba.commons.tests.validators.generic;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericValidatorTest {

    @Test
    void testNonNull() {
        GenericValidator validator = GenericValidator.of().nonNull();
        validator.validate("value");
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().nonNull();
        validator.validate(null);
        assertFalse(validator.isValid());
        assertEquals("is null", validator.getValidationErrors().get(0));
    }

    @Test
    void testNotEmpty() {
        GenericValidator validator = GenericValidator.of().notEmpty();
        validator.validate("a");
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().notEmpty();
        validator.validate(List.of(1));
        assertTrue(validator.isValid());

        validator = GenericValidator.of().notEmpty();
        validator.validate("");
        assertFalse(validator.isValid());
        assertEquals("is empty", validator.getValidationErrors().get(0));

        validator = GenericValidator.of().notEmpty();
        validator.validate(Collections.emptyList());
        assertFalse(validator.isValid());
    }

    @Test
    void testNotBlank() {
        GenericValidator validator = GenericValidator.of().notBlank();
        validator.validate("  a  ");
        assertTrue(validator.isValid());

        validator = GenericValidator.of().notBlank();
        validator.validate("   ");
        assertFalse(validator.isValid());
        assertEquals("is blank", validator.getValidationErrors().get(0));
    }

    @Test
    void testMin() {
        // String length
        GenericValidator validator = GenericValidator.of().min(3);
        validator.validate("abc");
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().min(3);
        validator.validate("ab");
        assertFalse(validator.isValid());
        assertEquals("must be greater than or equal to 3", validator.getValidationErrors().get(0));

        // Number value
        validator = GenericValidator.of().min(10);
        validator.validate(10);
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().min(10);
        validator.validate(9);
        assertFalse(validator.isValid());
    }

    @Test
    void testMax() {
        // String length
        GenericValidator validator = GenericValidator.of().max(3);
        validator.validate("abc");
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().max(3);
        validator.validate("abcd");
        assertFalse(validator.isValid());
        assertEquals("must be less than or equal to 3", validator.getValidationErrors().get(0));

        // Number value
        validator = GenericValidator.of().max(10);
        validator.validate(10);
        assertTrue(validator.isValid());

        validator = GenericValidator.of().max(10);
        validator.validate(11);
        assertFalse(validator.isValid());
    }

    @Test
    void testAssertTrue() {
        GenericValidator validator = GenericValidator.of().assertTrue();
        validator.validate(true);
        assertTrue(validator.isValid());

        validator = GenericValidator.of().assertTrue();
        validator.validate(false);
        assertFalse(validator.isValid());
        assertEquals("must be true", validator.getValidationErrors().get(0));
    }

    @Test
    void testAssertFalse() {
        GenericValidator validator = GenericValidator.of().assertFalse();
        validator.validate(false);
        assertTrue(validator.isValid());

        validator = GenericValidator.of().assertFalse();
        validator.validate(true);
        assertFalse(validator.isValid());
        assertEquals("must be false", validator.getValidationErrors().get(0));
    }

    @Test
    void testDecimalMinMax() {
        GenericValidator validator = GenericValidator.of().decimalMin("10.5", true);
        validator.validate(new BigDecimal("10.5"));
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().decimalMin("10.5", true);
        validator.validate(new BigDecimal("10.4"));
        assertFalse(validator.isValid());

        validator = GenericValidator.of().decimalMax("20.0", false);
        validator.validate(new BigDecimal("19.999"));
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().decimalMax("20.0", false);
        validator.validate(new BigDecimal("20.0"));
        assertFalse(validator.isValid());
    }
    
    @Test
    void testDigits() {
        GenericValidator validator = GenericValidator.of().digits(3, 2);
        validator.validate(new BigDecimal("123.45"));
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().digits(3, 2);
        validator.validate(new BigDecimal("1234.5")); // 4 integers
        assertFalse(validator.isValid());
        
        validator = GenericValidator.of().digits(3, 2);
        validator.validate(new BigDecimal("123.456")); // 3 fractions
        assertFalse(validator.isValid());
    }

    @Test
    void testEmail() {
        GenericValidator validator = GenericValidator.of().email();
        validator.validate("test@example.com");
        assertTrue(validator.isValid());

        validator = GenericValidator.of().email();
        validator.validate("invalid-email");
        assertFalse(validator.isValid());
        assertEquals("must be a well-formed email address", validator.getValidationErrors().get(0));
    }
    
    @Test
    void testPattern() {
        GenericValidator validator = GenericValidator.of().pattern("^[A-Z]{3}$");
        validator.validate("ABC");
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().pattern("^[A-Z]{3}$");
        validator.validate("abc");
        assertFalse(validator.isValid());
        assertTrue(validator.getValidationErrors().get(0).contains("must match"));
    }

    @Test
    void testFutureAndPast() {
        GenericValidator validator = GenericValidator.of().future();
        validator.validate(LocalDate.now().plusDays(1));
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().future();
        validator.validate(LocalDate.now().minusDays(1));
        assertFalse(validator.isValid());

        validator = GenericValidator.of().past();
        validator.validate(LocalDate.now().minusDays(1));
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of().past();
        validator.validate(LocalDate.now().plusDays(1));
        assertFalse(validator.isValid());
    }
    
    @Test
    void testSize() {
         GenericValidator validator = GenericValidator.of().size(2, 4);
         validator.validate("abc");
         assertTrue(validator.isValid());
         
         validator = GenericValidator.of().size(2, 4);
         validator.validate(List.of(1, 2, 3));
         assertTrue(validator.isValid());
         
         validator = GenericValidator.of().size(2, 4);
         validator.validate("a");
         assertFalse(validator.isValid());
         
         validator = GenericValidator.of().size(2, 4);
         validator.validate("abcde");
         assertFalse(validator.isValid());
    }
    
    @Test
    void testNumericSigns() {
        GenericValidator v = GenericValidator.of().positive();
        v.validate(1); assertTrue(v.isValid());
        
        v = GenericValidator.of().positive();
        v.validate(0); assertFalse(v.isValid()); v.getValidationErrors().clear();
        
        v = GenericValidator.of().positive();
        v.validate(-1); assertFalse(v.isValid());
        
        v = GenericValidator.of().positiveOrZero();
        v.validate(0); assertTrue(v.isValid());
        
        v = GenericValidator.of().negative();
        v.validate(-1); assertTrue(v.isValid());
        
        v = GenericValidator.of().negative();
        v.validate(0); assertFalse(v.isValid());
        
        v = GenericValidator.of().negativeOrZero();
        v.validate(0); assertTrue(v.isValid());
    }
}
