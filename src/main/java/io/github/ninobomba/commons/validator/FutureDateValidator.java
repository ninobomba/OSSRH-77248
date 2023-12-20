package io.github.ninobomba.commons.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String>
{

	@Override
	public void initialize( FutureDate constraintAnnotation ) { }

	@Override
	public boolean isValid(String input, ConstraintValidatorContext context)
	{
		if( StringUtils.isBlank( input ) ) return false;

		boolean delimiterSlash  = input.contains("/");
		boolean delimiterHyphen = input.contains("-");

		if( ! delimiterSlash && ! delimiterHyphen ) return false;

		String delimiter = delimiterSlash ? "/" : "-";
		String[] elements = input.split( delimiter );
		int size = elements.length;

		String yearFormat = getYearFormat( elements[ size - 1 ] );

		String pattern = switch ( size ) {
            case 1 -> yearFormat;
            case 2 -> "dd/"
                    .concat(getMonthFormat( elements[ 0 ] ) )
                    .concat("/" )
                    .concat( yearFormat );
            case 3 -> getDayFormat( elements[ 0 ] )
                    .concat("/" )
                    .concat( getMonthFormat( elements[ 1 ] ) )
                    .concat("/" )
                    .concat( yearFormat );
            default -> null;
        };

        if( size == 3 ) input = "01".concat( "/" ).concat( input );

		//Invalid pattern to validate future date
		if( Objects.isNull( pattern ) ) return false;

		LocalDateTime localDate  = LocalDate.now().minusMonths( 1 ).with( TemporalAdjusters.lastDayOfMonth() ).atTime( LocalTime.MAX );
		LocalDateTime parsedDate = LocalDateTime.from(LocalDate.parse(input, DateTimeFormatter.ofPattern( pattern )).atStartOfDay());

		return parsedDate.isAfter( localDate );
	}

	private String getYearFormat(String year){
		return year.length() == 2 ? "uu" : "uuuu";
	}

	private String getMonthFormat(String month){
		return month.length() == 2 ? "MM" : "M";
	}

	private String getDayFormat(String day){
		return day.length() == 2 ? "dd" : "d";
	}
}
