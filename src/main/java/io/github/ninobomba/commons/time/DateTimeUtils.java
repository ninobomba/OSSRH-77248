package io.github.ninobomba.commons.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateTimeUtils
{

    static String getNameByActualTimestamp()
    {
        return ""
                .concat( DateTimeFormatter.ofPattern( "yyyy-MM-dd-HH" ).format( LocalDateTime.now() ) )
                .concat( "-" )
                .concat( getMatchingMinuteIds() );
    }

    private static String getMatchingMinuteIds() {
        return LocalDateTime.now().getMinute() <= 30 ? "A" : "B";
    }

}
