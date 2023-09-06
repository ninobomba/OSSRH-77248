package io.github.ninobomba.commons.web.http;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface HttpRequestDataUtils
{

    // Headers
    static Map<String,String> getRequestHeadersMap(ServletRequest servletRequest) {
        return getRequestHeadersMap( ( HttpServletRequest ) servletRequest );
    }

//    static Map<String, String> printRequestHeaders(HttpServletRequest httpRequest)
//    {
//        var response = StreamSupport
//                .stream( Spliterators.spliteratorUnknownSize( httpRequest.getHeaderNames().asIterator(), Spliterator.ORDERED ), false)
//                .collect(
//                        HashMap::new, ( map, key ) -> map.put( key, httpRequest.getHeader( key ) ),
//                        HashMap::putAll
//                );
//        //.forEach( key -> log.info("HttpHeadersTools: printRequestHeaders() _: {} : {}", key, httpRequest.getHeader( key ) ) );
//        return response;
//    }

    static Map<String,String> getRequestHeadersMap(HttpServletRequest httpRequest)
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
    static Map<String,String> getRequestParametersMap(ServletRequest servletRequest) {
        return getRequestParametersMap( ( HttpServletRequest ) servletRequest);
    }

    static Map<String,String> getRequestParametersMap(HttpServletRequest httpRequest)
    {
        var map = new HashMap<String,String>();
        Stream
                .of( Collections.list ( httpRequest.getParameterNames() ) , Collections.list( httpRequest.getAttributeNames() ) )
                .flatMap( Collection::stream )
                .collect( Collectors.toList() )
                .forEach( key -> map.put( key, httpRequest.getParameter( key ) ) );
        return map;
    }

}
