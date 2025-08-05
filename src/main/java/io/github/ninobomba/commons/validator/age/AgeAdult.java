package io.github.ninobomba.commons.validator.age;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint ( validatedBy = AgeAdultValidator.class )
@Target ( { ElementType.FIELD } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface AgeAdult {

	String message ( ) default "Must be at least 18 years old";

	Class < ? >[] groups ( ) default { };

	Class < ? extends Payload >[] payload ( );
}
