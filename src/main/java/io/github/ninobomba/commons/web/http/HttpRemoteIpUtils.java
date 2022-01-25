package io.github.ninobomba.commons.web.http;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UtilityClass
public class HttpRemoteIpUtils
{

    @SneakyThrows
    public static String getRemoteIpByHttpRequestHeaders(HttpServletRequest request)
    {
        log.trace( "HttpRemoteIpUtils::getRemoteIpByHttpRequestHeaders() >: start" );

        final String HEADER_IP_REGEX = "X-FORWARDED-FOR|PROXY-CLIENT-IP|WL-PROXY-CLIENT-IP|HTTP-CLIENT-IP|ORIGIN|REFERER";

        Map<String,String> headers = HttpRequestDataUtils.getRequestHeadersMap( request );

        if( CollectionUtils.isEmpty( headers) ) return null;

        String ip =
                Optional.of(
                        headers.entrySet()
                        .stream()
                        .filter(m -> StringUtils.containsAny(m.getKey().toUpperCase(), HEADER_IP_REGEX))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .orElseGet(request::getRemoteHost)
                ).orElse(
                        InetAddress.getLocalHost().getHostAddress()
                );

        log.trace( "HttpRemoteIpUtils::getRemoteIpByHttpRequestHeaders() <: complete" );

        return ip;
    }


}
