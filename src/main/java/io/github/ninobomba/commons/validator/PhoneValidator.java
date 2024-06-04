package io.github.ninobomba.commons.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The PhoneValidator class implements the ConstraintValidator interface to define
 * the validation logic for the @Phone annotation. It checks whether a given phone number
 * string is valid based on specific constraints.
 * <p>
 * A phone number is considered valid if it is a 10-digit number or follows one of the common
 * phone number formats:
 * - NNN-NNN-NNNN
 * - (NNN) NNN-NNNN
 * - NNN NNN NNNN
 * - NNN.NNN.NNNN
 * - NNN-NNN-NNNN xNNNNN
 * - (NNN)-NNN-NNNN
 * - (NNN) NNN-NNNN
 * <p>
 * ```
 *
 * @see Phone
 */
public class PhoneValidator implements ConstraintValidator < Phone, String > {

	@Override
	public void initialize ( Phone phone ) {
	}

	@Override
	public boolean isValid ( String phone, ConstraintValidatorContext ctx ) {
		if ( StringUtils.isBlank ( phone ) ) return false;

		if ( phone.matches ( "\\d{10}" ) ) return true;
		else if ( phone.matches ( "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$" ) ) return true;
		else if ( phone.matches ( "\\d{3}[-.\\s]\\d{3}[-.\\s]\\d{4}" ) ) return true;
		else if ( phone.matches ( "\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}" ) ) return true;
		else if ( phone.matches ( "\\(\\d{3}\\)-\\d{3}-\\d{4}" ) ) return true;
		else return phone.matches ( "\\(\\d{3}\\)\\s+\\d{3}-\\d{4}" );
	}

}
