package io.github.ninobomba.commons.validator.phone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The Phone annotation is used to mark a field as a phone number and provides validation for the phone number format.
 * This annotation is applied to fields and is validated by the PhoneValidator class.
 * <p>
 * Usage Example:
 * - Annotate the phone number field with the @Phone annotation
 * <p>
 * Constraints:
 * - The annotated field must be of type String
 * - The phone number must be a valid 10-digit number or follow one of the common phone number formats:
 * - NNN-NNN-NNNN
 * - (NNN) NNN-NNNN
 * - NNN NNN NNNN
 * - NNN.NNN.NNNN
 * - NNN-NNN-NNNN xNNNNN
 * - (NNN)-NNN-NNNN
 * - (NNN) NNN-NNNN
 * Note: This is just the documentation for the Phone annotation. The implementation details are provided for reference purposes and should not be used directly.
 * PhoneValidator is responsible for the actual validation of the annotated field.
 *
 * @see PhoneValidator
 */
@Documented
@Constraint ( validatedBy = PhoneValidator.class )
@Target ( { ElementType.FIELD } )
@Retention ( RetentionPolicy.RUNTIME )
public @interface Phone {
	String message ( ) default "Invalid Phone Number";

	Class < ? >[] groups ( ) default { };

	Class < ? extends Payload >[] payload ( ) default { };
}
