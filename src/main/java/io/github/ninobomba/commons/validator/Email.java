package io.github.ninobomba.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Inherited
@Constraint ( validatedBy = EmailValidator.class )
@Target ( { ElementType.FIELD } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface Email {
	String message ( ) default "Invalid Email";
	
	Class < ? >[] groups ( ) default { };
	
	Class < ? extends Payload >[] payload ( ) default { };
}
