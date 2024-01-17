package io.github.ninobomba.commons.web.http;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public interface HttpResponseDataUtils {
	static Map < String, String > getResponseHeadersMap ( ServletResponse servletResponse ) {
		return getResponseHeadersMap ( ( HttpServletResponse ) servletResponse );
	}
	
	static Map < String, String > getResponseHeadersMap ( HttpServletResponse httpResponse ) {
		return httpResponse.getHeaderNames ( ).stream ( ).collect ( toMap ( key -> key, httpResponse::getHeader ) );
	}
	
}
