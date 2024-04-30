package io.github.ninobomba.commons.web.http;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The HttpRemoteIpUtils class provides utilities for retrieving the remote IP address from HTTP request headers.
 */
public interface HttpRemoteIpUtils {
	
	/**
	 * Retrieves the remote IP address from HTTP request headers.
	 *
	 * @param request the HttpServletRequest containing the request headers
	 * @return the remote IP address as a String, or null if no IP address is found in the headers
	 */
	@SneakyThrows
	static String getRemoteIpByHttpRequestHeaders ( HttpServletRequest request ) {
		var headers = HttpRequestDataUtils.getRequestHeadersMap ( request );
		
		if ( Objects.isNull ( headers ) || headers.isEmpty ( ) ) return null;
		
		String HEADER_IP_REGEX = "X-FORWARDED-FOR|PROXY-CLIENT-IP|WL-PROXY-CLIENT-IP|HTTP-CLIENT-IP|ORIGIN|REFERER";
		
		return
				Optional.ofNullable ( headers.entrySet ( ).stream ( )
						.filter ( e -> StringUtils.containsAny ( e.getKey ( ).toUpperCase ( ), HEADER_IP_REGEX ) )
						.map ( Map.Entry::getValue )
						.findFirst ( )
						.orElseGet ( request::getRemoteHost )
				).orElse (
						InetAddress.getLocalHost ( ).getHostAddress ( )
				);
	}
	
}
