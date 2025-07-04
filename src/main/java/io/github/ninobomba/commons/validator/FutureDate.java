package io.github.ninobomba.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The FutureDate annotation is used to mark a field that should represent a date in the future.
 * It is applied to fields and is implemented using the ConstraintValidator interface by the FutureDateValidator class.
 * FutureDateValidator validates whether the given date string represents a date in the future or not.
 */
@Documented
@Constraint ( validatedBy = FutureDateValidator.class )
@Target ( { ElementType.FIELD } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface FutureDate {
	String message ( ) default "Invalid Future Date";
	
	Class < ? >[] groups ( ) default { };
	
	Class < ? extends Payload >[] payload ( ) default { };
}
