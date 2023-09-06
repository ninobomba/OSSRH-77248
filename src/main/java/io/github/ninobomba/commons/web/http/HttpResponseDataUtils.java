package io.github.ninobomba.commons.web.http;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public interface HttpResponseDataUtils
{
    static Map<String,String> getResponseHeadersMap( ServletResponse servletResponse ) {
        return getResponseHeadersMap( ( HttpServletResponse ) servletResponse);
    }

    static Map<String,String> getResponseHeadersMap( HttpServletResponse httpResponse ) {
        var map = new HashMap<String,String>();
        httpResponse.getHeaderNames().forEach( key -> map.put( key, httpResponse.getHeader( key ) ) );
        return map;
        
//        return Collections
//                .list( Optional.ofNullable( httpResponse.getHeaderNames() ).orElse( Collections.emptyList() )
//                .stream()
//                .collect(HashMap::new, ( map, key ) -> map.put( key, httpResponse.getHeader( key ) ), HashMap::putAll);
    }

}
