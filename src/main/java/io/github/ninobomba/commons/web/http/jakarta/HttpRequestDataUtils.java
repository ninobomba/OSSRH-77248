package io.github.ninobomba.commons.web.http.jakarta;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

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
						TreeMap :: new ,
						( map , key ) -> map.put ( key , httpRequest.getHeader ( key ) ) ,
						TreeMap :: putAll
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
		return Stream.of ( Collections.list ( httpRequest.getParameterNames ( ) ) , Collections.list ( httpRequest.getAttributeNames ( ) ) )
				.flatMap ( Collection :: stream )
				.collect ( toMap ( key -> key , httpRequest :: getParameter ) );
	}

	/**
	 * Retrieves the value of a parameter from the specified HTTP request, checking first in the headers
	 * and then in the query string parameters.
	 * If the parameter is found in both, the header value is returned.
	 *
	 * @param httpRequest the HttpServletRequest from which to search for the parameter
	 * @param name        the name of the parameter to search for
	 * @return the value of the parameter as a String if found, or null if the parameter is not present in either the headers or query parameters
	 */
	static String getParameterFromHeaderOrQueryParameter ( HttpServletRequest httpRequest , String name ) {
		var header = httpRequest.getHeader ( name );
		if ( Objects.nonNull ( header ) ) return header;

		var parameter = httpRequest.getParameter ( name );
		if ( Objects.nonNull ( parameter ) ) return parameter;

		return null;
	}

}
