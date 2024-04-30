package io.github.ninobomba.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The Email class is a custom annotation used for validating email addresses.
 * It is used to annotate fields and ensure that the value provided in the field follows a standard email format.
 *
 * <p>The Email annotation should be placed on a field and will be validated by the EmailValidator class.
 *
 * <p>Example usage:
 * <pre>
 *     {@literal @}Email
 *     private String email;
 * </pre>
 *
 * @see EmailValidator
 */
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
