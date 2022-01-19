package tech.tools4monkeys.commons.time;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@UtilityClass
public final class DateTimeUtils
{

    public static String getNameByActualTimestamp()
    {
        return ""
                .concat( DateTimeFormatter.ofPattern( "yyyy-MM-dd-HH" ).format( LocalDateTime.now() ) )
                .concat( "-" )
                .concat( getMatchingMinuteIds() );
    }

    private static String getMatchingMinuteIds() {
        return Calendar.getInstance().get( Calendar.MINUTE ) <= 30 ? "A" : "B";
    }

}
