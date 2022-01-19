package tech.tools4monkeys.commons.exceptions.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.StringJoiner;

@UtilityClass
public class ExceptionUtils
{

    @SneakyThrows
    public static String convertToString(Throwable throwable )
    {
        if ( Objects.isNull( throwable) ) return null;

        var traces = throwable.getStackTrace();

        var response = new StringJoiner("\n");

        if ( Objects.nonNull( traces ) && traces.length > 0 )
        {
            response.add(throwable.getClass() + ": " + throwable.getMessage());
            for (StackTraceElement trace : traces)
                response.add("    at " + trace.getClassName() + '.' + trace.getMethodName() + '(' + trace.getFileName() + ':' + trace.getLineNumber() + ')');
        }

        Throwable cause = throwable.getCause();
        while ( Objects.nonNull( cause ) )
        {
            StackTraceElement[] causeTraces = cause.getStackTrace();
            if ( Objects.nonNull( causeTraces ) && causeTraces.length > 0 )
            {
                response.add( "Caused By:" );
                response.add( cause.getClass() + ": " + cause.getMessage());
                for (StackTraceElement causeTrace : causeTraces)
                    response.add("    at " + causeTrace.getClassName() + '.' + causeTrace.getMethodName() + '(' + causeTrace.getFileName() + ':' + causeTrace.getLineNumber() + ')');
            }
            cause = cause.getCause();
        }

        return response.toString();
    }

}
