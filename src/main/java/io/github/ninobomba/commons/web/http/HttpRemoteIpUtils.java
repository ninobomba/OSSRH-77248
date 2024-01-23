package io.github.ninobomba.commons.web.http;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Map;
import java.util.Optional;

public interface HttpRemoteIpUtils {
	
	@SneakyThrows
	static String getRemoteIpByHttpRequestHeaders ( HttpServletRequest request ) {
		var headers = HttpRequestDataUtils.getRequestHeadersMap ( request );
		
		if ( CollectionUtils.isEmpty ( headers ) ) return null;
		
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
