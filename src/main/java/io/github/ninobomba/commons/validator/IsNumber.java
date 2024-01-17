package io.github.ninobomba.commons.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint ( validatedBy = NumberValidator.class )
@Target ( { ElementType.FIELD } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface IsNumber {
	String message ( ) default "Invalid Number";
	
	Class < ? >[] groups ( ) default { };
	
	Class < ? extends Payload >[] payload ( ) default { };
}
