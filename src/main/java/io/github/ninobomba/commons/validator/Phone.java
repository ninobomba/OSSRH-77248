package io.github.ninobomba.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint ( validatedBy = PhoneValidator.class )
@Target ( {  ElementType.FIELD } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface Phone {
	String message ( ) default "Invalid Phone Number";
	
	Class < ? >[] groups ( ) default { };
	
	Class < ? extends Payload >[] payload ( ) default { };
}
