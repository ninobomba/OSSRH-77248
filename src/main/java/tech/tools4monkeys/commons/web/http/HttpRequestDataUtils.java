package tech.tools4monkeys.commons.web.http;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.SequenceInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@UtilityClass
public class HttpRequestDataUtils
{

    public static void printRequestHeaders(HttpServletRequest httpRequest)
    {
        StreamSupport
                .stream( Spliterators.spliteratorUnknownSize( httpRequest.getHeaderNames().asIterator(), Spliterator.ORDERED ), false)
                .forEach( key -> log.info("HttpHeadersTools: printRequestHeaders() _: {} : {}", key, httpRequest.getHeader( key ) ) );
    }

    // Headers
    public static Map<String,String> getRequestHeadersMap(ServletRequest servletRequest) {
        return getRequestHeadersMap( (HttpServletRequest ) servletRequest );
    }

    public static Map<String,String> getRequestHeadersMap(HttpServletRequest httpRequest)
    {
        return Collections
                .list( Optional.ofNullable( httpRequest.getHeaderNames() ).orElse( Collections.emptyEnumeration() ) )
                .stream()
                .collect(
                        HashMap::new,
                        ( map, key ) -> map.put( key, httpRequest.getHeader( key ) ),
                        HashMap::putAll
                );
    }

    // Parameters
    public static Map<String,String> getRequestParametersMap(ServletRequest servletRequest) {
        return getRequestParametersMap( ( HttpServletRequest ) servletRequest);
    }

    public static Map<String,String> getRequestParametersMap(HttpServletRequest httpRequest)
    {
        Map<String,String> map = new HashMap<>();
        Stream
                .of( Collections.list ( httpRequest.getParameterNames() ) , Collections.list( httpRequest.getAttributeNames() ) )
                .flatMap( Collection::stream )
                .collect( Collectors.toList() )
                .forEach( key -> map.put( key, httpRequest.getParameter( key ) ) );
        return map;
    }

}
