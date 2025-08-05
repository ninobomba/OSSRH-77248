package io.github.ninobomba.commons.validator.number;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The IsNumber annotation is used to validate that a field contains a valid number.
 * It is a constraint annotation that is used in conjunction with the NumberValidator class.
 *
 * <p>Usage:</p>
 * <pre>{@code
 * @IsNumber
 * private String number;
 * }</pre>
 *
 * <p>The IsNumber annotation can be applied to fields of type String.</p>
 *
 * <p>The default error message for failed validation is "Invalid Number".</p>
 *
 * @see NumberValidator
 */
@Documented
@Constraint ( validatedBy = NumberValidator.class )
@Target ( { ElementType.FIELD } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface IsNumber {
	String message ( ) default "Invalid Number";

	Class < ? >[] groups ( ) default { };

	Class < ? extends Payload >[] payload ( ) default { };
}
