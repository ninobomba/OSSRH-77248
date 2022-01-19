package io.github.ninobomba.commons.web.http;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@UtilityClass
public class HttpResponseDataUtils
{

    public static void printResponseHeaders( HttpServletResponse httpResponse ) {
        httpResponse
                .getHeaderNames()
                .forEach( key -> log.info("HttpResponseDataUtils: printResponseHeaders() _: {} : {}", key, httpResponse.getHeader( key ) ) );
    }

    public static Map<String,String> getResponseHeadersMap( ServletResponse servletResponse ) {
        return getResponseHeadersMap( ( HttpServletResponse ) servletResponse);
    }

    public static Map<String,String> getResponseHeadersMap( HttpServletResponse httpResponse ) {
        Map<String,String> map = new HashMap<>();
        httpResponse
                .getHeaderNames()
                .forEach( key -> map.put( key, httpResponse.getHeader( key ) ) );
        return map;
    }

}
