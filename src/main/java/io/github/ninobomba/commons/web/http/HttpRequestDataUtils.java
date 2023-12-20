package io.github.ninobomba.commons.web.http;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public interface HttpRequestDataUtils
{

    // Headers
    static Map<String,String> getRequestHeadersMap(ServletRequest servletRequest) {
        return getRequestHeadersMap( ( HttpServletRequest ) servletRequest );
    }

    static Map<String,String> getRequestHeadersMap( HttpServletRequest httpRequest )
    {
        return Collections
                .list( Optional.ofNullable( httpRequest.getHeaderNames() ).orElse( Collections.emptyEnumeration() ) )
                .stream()
                .collect(
                        TreeMap::new,
                        ( map, key ) -> map.put( key, httpRequest.getHeader( key ) ),
                        TreeMap::putAll
                );
    }

    // Parameters
    static Map<String,String> getRequestParametersMap(ServletRequest servletRequest) {
        return getRequestParametersMap( ( HttpServletRequest ) servletRequest );
    }

    static Map<String,String> getRequestParametersMap(HttpServletRequest httpRequest)
    {
        return
        Stream.of( Collections.list ( httpRequest.getParameterNames() ) , Collections.list( httpRequest.getAttributeNames() ) )
                .flatMap( Collection::stream )
                .collect( toMap( key -> key, httpRequest::getParameter ) );
    }
}
