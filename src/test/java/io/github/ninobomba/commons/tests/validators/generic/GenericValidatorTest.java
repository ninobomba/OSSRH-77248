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
        GenericValidator validator = GenericValidator.of("value").isNotNull();
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of(null).isNotNull();
        assertFalse(validator.isValid());
        assertEquals("is null", validator.getValidationErrors().get(0));
    }

    @Test
    void testNotEmpty() {
        GenericValidator validator = GenericValidator.of("a").isNotEmpty();
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of(List.of(1)).isNotEmpty();
        assertTrue(validator.isValid());

        validator = GenericValidator.of("").isNotEmpty();
        assertFalse(validator.isValid());
        assertEquals("is empty", validator.getValidationErrors().get(0));

        validator = GenericValidator.of(Collections.emptyList()).isNotEmpty();
        assertFalse(validator.isValid());
    }

    @Test
    void testNotBlank() {
        GenericValidator validator = GenericValidator.of("  a  ").isNotBlank();
        assertTrue(validator.isValid());

        validator = GenericValidator.of("   ").isNotBlank();
        assertFalse(validator.isValid());
        assertEquals("is blank", validator.getValidationErrors().get(0));
    }

    @Test
    void testMin() {
        // String length
        GenericValidator validator = GenericValidator.of("abc").hasMin(3);
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of("ab").hasMin(3);
        assertFalse(validator.isValid());
        assertEquals("must be greater than or equal to 3", validator.getValidationErrors().get(0));

        // Number value
        validator = GenericValidator.of(10).hasMin(10);
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of(9).hasMin(10);
        assertFalse(validator.isValid());
    }

    @Test
    void testMax() {
        // String length
        GenericValidator validator = GenericValidator.of("abc").hasMax(3);
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of("abcd").hasMax(3);
        assertFalse(validator.isValid());
        assertEquals("must be less than or equal to 3", validator.getValidationErrors().get(0));

        // Number value
        validator = GenericValidator.of(10).hasMax(10);
        assertTrue(validator.isValid());

        validator = GenericValidator.of(11).hasMax(10);
        assertFalse(validator.isValid());
    }

    @Test
    void testAssertTrue() {
        GenericValidator validator = GenericValidator.of(true).isTrue();
        assertTrue(validator.isValid());

        validator = GenericValidator.of(false).isTrue();
        assertFalse(validator.isValid());
        assertEquals("must be true", validator.getValidationErrors().get(0));
    }

    @Test
    void testAssertFalse() {
        GenericValidator validator = GenericValidator.of(false).isFalse();
        assertTrue(validator.isValid());

        validator = GenericValidator.of(true).isFalse();
        assertFalse(validator.isValid());
        assertEquals("must be false", validator.getValidationErrors().get(0));
    }

    @Test
    void testDecimalMinMax() {
        GenericValidator validator = GenericValidator.of(new BigDecimal("10.5")).hasDecimalMin("10.5", true);
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of(new BigDecimal("10.4")).hasDecimalMin("10.5", true);
        assertFalse(validator.isValid());

        validator = GenericValidator.of(new BigDecimal("19.999")).hasDecimalMax("20.0", false);
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of(new BigDecimal("20.0")).hasDecimalMax("20.0", false);
        assertFalse(validator.isValid());
    }
    
    @Test
    void testDigits() {
        GenericValidator validator = GenericValidator.of(new BigDecimal("123.45")).hasDigits(3, 2);
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of(new BigDecimal("1234.5")).hasDigits(3, 2); // 4 integers
        assertFalse(validator.isValid());
        
        validator = GenericValidator.of(new BigDecimal("123.456")).hasDigits(3, 2); // 3 fractions
        assertFalse(validator.isValid());
    }

    @Test
    void testEmail() {
        GenericValidator validator = GenericValidator.of("test@example.com").isEmail();
        assertTrue(validator.isValid());

        validator = GenericValidator.of("invalid-email").isEmail();
        assertFalse(validator.isValid());
        assertEquals("must be a well-formed email address", validator.getValidationErrors().get(0));
    }
    
    @Test
    void testPattern() {
        GenericValidator validator = GenericValidator.of("ABC").matches("^[A-Z]{3}$");
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of("abc").matches("^[A-Z]{3}$");
        assertFalse(validator.isValid());
        assertTrue(validator.getValidationErrors().get(0).contains("must match"));
    }

    @Test
    void testFutureAndPast() {
        GenericValidator validator = GenericValidator.of(LocalDate.now().plusDays(1)).isInFuture();
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of(LocalDate.now().minusDays(1)).isInFuture();
        assertFalse(validator.isValid());

        validator = GenericValidator.of(LocalDate.now().minusDays(1)).isInPast();
        assertTrue(validator.isValid());
        
        validator = GenericValidator.of(LocalDate.now().plusDays(1)).isInPast();
        assertFalse(validator.isValid());
    }
    
    @Test
    void testSize() {
         GenericValidator validator = GenericValidator.of("abc").hasSize(2, 4);
         assertTrue(validator.isValid());
         
         validator = GenericValidator.of(List.of(1, 2, 3)).hasSize(2, 4);
         assertTrue(validator.isValid());
         
         validator = GenericValidator.of("a").hasSize(2, 4);
         assertFalse(validator.isValid());
         
         validator = GenericValidator.of("abcde").hasSize(2, 4);
         assertFalse(validator.isValid());
    }
    
    @Test
    void testNumericSigns() {
        assertTrue(GenericValidator.of(1).isPositive().isValid());
        assertFalse(GenericValidator.of(0).isPositive().isValid());
        assertFalse(GenericValidator.of(-1).isPositive().isValid());
        
        assertTrue(GenericValidator.of(0).isPositiveOrZero().isValid());
        
        assertTrue(GenericValidator.of(-1).isNegative().isValid());
        assertFalse(GenericValidator.of(0).isNegative().isValid());
        
        assertTrue(GenericValidator.of(0).isNegativeOrZero().isValid());
    }

    @Test
    void testStringOperations() {
        assertTrue(GenericValidator.of("Apple").startsWith("A").isValid());
        GenericValidator v = GenericValidator.of("Banana").startsWith("A");
        assertFalse(v.isValid());
        assertEquals("must start with \"A\"", v.getValidationErrors().get(0));

        assertTrue(GenericValidator.of("Buzz").endsWith("z").isValid());
        v = GenericValidator.of("Bar").endsWith("z");
        assertFalse(v.isValid());
        assertEquals("must end with \"z\"", v.getValidationErrors().get(0));

        assertTrue(GenericValidator.of("Book").contains("oo").isValid());
        v = GenericValidator.of("Bak").contains("oo");
        assertFalse(v.isValid());
        assertEquals("must contain \"oo\"", v.getValidationErrors().get(0));
    }

    @Test
    void testIn() {
        assertTrue(GenericValidator.of("B").isIn("A", "B", "C").isValid());
        
        GenericValidator v = GenericValidator.of("D").isIn("A", "B", "C");
        assertFalse(v.isValid());
        assertTrue(v.getValidationErrors().get(0).contains("must be one of"));
    }

    @Test
    void testRange() {
        assertTrue(GenericValidator.of(15).isInRange(10, 20).isValid());
        assertTrue(GenericValidator.of(10).isInRange(10, 20).isValid());
        assertTrue(GenericValidator.of(20).isInRange(10, 20).isValid());
        
        GenericValidator v = GenericValidator.of(9).isInRange(10, 20);
        assertFalse(v.isValid());
        assertEquals("must be between 10 and 20", v.getValidationErrors().get(0));
        
        assertFalse(GenericValidator.of(21).isInRange(10, 20).isValid());
    }

    @Test
    void testHasErrors() {
        GenericValidator v = GenericValidator.of(null).isNotNull();
        assertTrue(v.hasErrors());
        assertFalse(v.isValid());
        
        v = GenericValidator.of("ok").isNotNull();
        assertFalse(v.hasErrors());
        assertTrue(v.isValid());
    }
    
    @Test
    void testChaining() {
        GenericValidator v = GenericValidator.of("test@example.com")
                .isNotNull()
                .isNotEmpty()
                .isEmail()
                .startsWith("test");
        assertTrue(v.isValid());
        
        v = GenericValidator.of("invalid")
                .isNotNull()
                .isEmail();
        assertFalse(v.isValid());
    }
}
