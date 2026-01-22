package io.github.ninobomba.commons.validator.array;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint ( validatedBy = NotBlankCharArrayValidator.class )
@Target ( { ElementType.FIELD, ElementType.PARAMETER } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface NotBlankCharArray {

	String message ( ) default "Field cannot be blank";

	Class < ? >[] groups ( ) default { };

	Class < ? extends Payload >[] payload ( ) default { };
}
