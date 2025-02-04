package io.github.ninobomba.commons.web.http.javax;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * The HttpRequestDataUtils interface provides utility methods for working with HTTP request data.
 */
public interface HttpRequestDataUtils {

	/**
	 * Returns a map of request headers from the given ServletRequest.
	 *
	 * @param servletRequest the ServletRequest object from which to extract the headers
	 * @return a map containing the request headers, where the key is the header name and the value is the header value
	 */
	// Headers
	static Map < String, String > getRequestHeadersMap ( ServletRequest servletRequest ) {
		return getRequestHeadersMap ( ( HttpServletRequest ) servletRequest );
	}

	/**
	 * Returns a map of request headers from the given HttpServletRequest object.
	 *
	 * @param httpRequest the HttpServletRequest object from which to extract the headers
	 * @return a map containing the request headers, where the key is the header name and the value is the header value
	 */
	static Map < String, String > getRequestHeadersMap ( HttpServletRequest httpRequest ) {
		return Collections
				.list ( Optional.ofNullable ( httpRequest.getHeaderNames ( ) ).orElse ( Collections.emptyEnumeration ( ) ) )
				.stream ( )
				.collect (
						TreeMap::new,
						( map, key ) -> map.put ( key, httpRequest.getHeader ( key ) ),
						TreeMap::putAll
				);
	}

	/**
	 * Returns a map of request parameters from the given ServletRequest.
	 *
	 * @param servletRequest the ServletRequest object from which to extract the parameters
	 * @return a map containing the request parameters, where the key is the parameter name and the value is the parameter value
	 */
	// Parameters
	static Map < String, String > getRequestParametersMap ( ServletRequest servletRequest ) {
		return getRequestParametersMap ( ( HttpServletRequest ) servletRequest );
	}

	/**
	 * Returns a map of request parameters from the given HttpServletRequest object.
	 *
	 * @param httpRequest the HttpServletRequest object from which to extract the parameters
	 * @return a map containing the request parameters, where the key is the parameter name and the value is the parameter value
	 */
	static Map < String, String > getRequestParametersMap ( HttpServletRequest httpRequest ) {
		return Stream.of ( Collections.list ( httpRequest.getParameterNames ( ) ), Collections.list ( httpRequest.getAttributeNames ( ) ) )
				.flatMap ( Collection::stream )
				.collect ( toMap ( key -> key, httpRequest::getParameter ) );
	}

	/**
	 * Returns a map of request parameters from the given HttpServletRequest object.
	 *
	 * @param httpRequest the HttpServletRequest object from which to extract the parameters
	 * @return a map containing the request parameters, where the key is the parameter name, and the value is the parameter value
	 */
	static Map < String, String > getRequestParametersMap ( jakarta.servlet.http.HttpServletRequest httpRequest ) {
		return Stream.of ( Collections.list ( httpRequest.getParameterNames ( ) ), Collections.list ( httpRequest.getAttributeNames ( ) ) )
				.flatMap ( Collection::stream )
				.collect ( toMap ( key -> key, httpRequest::getParameter ) );
	}
}
