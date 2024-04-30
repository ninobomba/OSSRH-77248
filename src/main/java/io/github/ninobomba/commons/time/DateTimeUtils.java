package io.github.ninobomba.commons.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This interface contains utility methods for working with date and time.
 */
public interface DateTimeUtils {
	
	/**
	 * Returns a string representing the name derived from the current timestamp.
	 * The format of the name is 'yyyy-MM-dd-HH-MinuteId', where 'yyyy' represents
	 * the four-digit year, 'MM' represents the two-digit month, 'dd' represents
	 * the two-digit day, 'HH' represents the two-digit hour, and 'MinuteId'
	 * represents a single character 'A' or 'B' denoting the matching minute.
	 *
	 * @return a string representing the name derived from the current timestamp
	 */
	static String getNameByActualTimestamp ( ) {
		return ""
				.concat ( DateTimeFormatter.ofPattern ( "yyyy-MM-dd-HH" ).format ( LocalDateTime.now ( ) ) )
				.concat ( "-" )
				.concat ( getMatchingMinuteIds ( ) );
	}
	
	/**
	 * Returns a string representing the minute identifier for the current timestamp.
	 * The minute identifier is a single character 'A' or 'B', which represents whether
	 * the minute of the current timestamp is less than or equal to 30 (A) or greater than 30 (B).
	 *
	 * @return a string representing the minute identifier ('A' or 'B') for the current timestamp
	 */
	private static String getMatchingMinuteIds ( ) {
		return LocalDateTime.now ( ).getMinute ( ) <= 30 ? "A" : "B";
	}
	
}
