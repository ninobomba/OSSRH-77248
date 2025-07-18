package io.github.ninobomba.commons.web.http.javax;

import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.net.HttpHeaders.X_FORWARDED_FOR;
import static org.springframework.http.HttpHeaders.REFERER;

/**
 * The HttpRemoteIpUtils class provides utilities for retrieving the remote IP address from HTTP request headers.
 */
public interface HttpRemoteIpUtils {

    List< String > HEADER_IP_REGEX_LIST = List.of(
            X_FORWARDED_FOR,
            REFERER,
            "PROXY-CLIENT-IP",
            "WL-PROXY-CLIENT-IP",
            "HTTP-CLIENT-IP",
            "ORIGIN",
            "HOST" );

    /**
     * Retrieves the remote IP address from HTTP request headers.
     *
     * @param request the HttpServletRequest containing the request headers
     * @return the remote IP address as a String, or null if no IP address is found in the headers
     */
    @SneakyThrows
    static String getRemoteIpByHttpRequestHeaders ( HttpServletRequest request ) {

        var headers = HttpRequestDataUtils.getRequestHeadersMap( request );

        if ( Objects.isNull( headers ) || headers.isEmpty( ) ) return null;

        return Optional.ofNullable( headers.entrySet( ).stream( )
                .filter( e -> HEADER_IP_REGEX_LIST.contains( e.getKey( ).toUpperCase( ) ) )
                .map( Map.Entry::getValue )
                .findFirst( )
                .orElseGet( request::getRemoteHost )
        ).orElse(
                InetAddress.getLocalHost( ).getHostAddress( )
        );
    }

}
