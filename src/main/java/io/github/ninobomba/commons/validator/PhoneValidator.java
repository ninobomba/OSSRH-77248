package io.github.ninobomba.commons.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String>
{

	@Override
	public void initialize(Phone phone) { }

	@Override
	public boolean isValid(String phone, ConstraintValidatorContext ctx)
	{
		if( StringUtils.isBlank( phone ) ) return false;

		if (phone.matches("\\d{10}")) return true;
        else if(phone.matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")) return true;
        else if(phone.matches("\\d{3}[-.\\s]\\d{3}[-.\\s]\\d{4}")) return true;
        else if(phone.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
        else if(phone.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
        else return phone.matches("\\(\\d{3}\\)\\s+\\d{3}-\\d{4}");
	}

}
