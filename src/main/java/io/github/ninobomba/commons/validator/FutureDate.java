package io.github.ninobomba.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint ( validatedBy = FutureDateValidator.class )
@Target ( { ElementType.FIELD } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface FutureDate {
	String message ( ) default "Invalid Future Date";
	
	Class < ? >[] groups ( ) default { };
	
	Class < ? extends Payload >[] payload ( ) default { };
}
