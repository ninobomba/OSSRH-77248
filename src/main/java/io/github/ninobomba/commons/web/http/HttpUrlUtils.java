package io.github.ninobomba.commons.web.http;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class HttpUrlUtils
{

    @SneakyThrows
    public static URL buildURL(String path ) {
        return new URL( path );
    }

    @SneakyThrows
    public static URI buildURI(String path ) {
        return new URI( path );
    }

    @SneakyThrows
    public static Map<String,String> parseUrlWithParameters(String uri)
    {
        var response = new HashMap<String,String>();

        if( StringUtils.isBlank( uri ) ) return response;

        var url = new URL( uri );

        response.put("authority",  url.getAuthority());
        response.put("protocol",   url.getProtocol());
        response.put("host",       url.getHost());
        response.put("port",       String.valueOf(url.getPort()));
        response.put("path",       url.getPath());
        response.put("query",      url.getQuery());
        response.put("filename",   url.getFile());
        response.put("ref",        url.getRef());

        return response;
    }

    public static boolean hostAvailabilityCheck(String url) {
        return hostAvailabilityCheck(url, 0, 10_000);
    }

    public static boolean hostAvailabilityCheck(String url, int port) {
        return hostAvailabilityCheck(url, port, 10_000);
    }

    @SneakyThrows
    public static boolean hostAvailabilityCheck(String url, int port, int timeout)
    {
        url = url.replaceFirst("^https", "http"); // Otherwise, an exception may be thrown on invalid SSL certificates.
        HttpURLConnection connection = null;
        try {
            String uri = url;
            if( port > 0 ) uri += ":" + port;
            connection = (HttpURLConnection) new URL( uri ).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return 200 <= responseCode && responseCode <= 399;
        } catch (IOException exception) {
            return false;
        } finally {
            if(Objects.nonNull( connection )) connection.disconnect();
        }
    }
}
