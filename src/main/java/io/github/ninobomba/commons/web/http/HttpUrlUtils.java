package io.github.ninobomba.commons.web.http;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public interface HttpUrlUtils
{

    @SneakyThrows
    static URL buildURL( String path ) {
        return new URI( path ).toURL();
    }

    @SneakyThrows
    static URI buildURI( String path ) {
        return new URI( path );
    }

    @SneakyThrows
    static Map<String,String> parseUrlWithParameters( String uri )
    {
        var response = new HashMap<String,String>();

        if( StringUtils.isBlank( uri ) ) return response;

        var url = new URI( uri ).toURL();

        response.put("authority",  url.getAuthority());
        response.put("protocol",   url.getProtocol());
        response.put("host",       url.getHost());
        response.put("port",       String.valueOf( url.getPort() ) );
        response.put("path",       url.getPath());
        response.put("query",      url.getQuery());
        response.put("filename",   url.getFile());
        response.put("ref",        url.getRef());

        return response;
    }

    static boolean hostAvailabilityCheck(String url) {
        return hostAvailabilityCheck(url, 0, 10_000);
    }

    static boolean hostAvailabilityCheck(String url, int port) {
        return hostAvailabilityCheck(url, port, 10_000);
    }

    @SneakyThrows
    static boolean hostAvailabilityCheck(String url, int port, int timeout)
    {
        url = url.replaceFirst("^https", "http"); // Otherwise, an exception may be thrown on invalid SSL certificates.

        HttpURLConnection connection = null;
        try {
            String uri = port > 0 ? url.concat(":" + port ) : url;
            connection = (HttpURLConnection) new URI( uri ).toURL().openConnection();
            connection.setConnectTimeout( timeout );
            connection.setReadTimeout( timeout );
            connection.setRequestMethod( "HEAD" );
            int responseCode = connection.getResponseCode();
            return 200 <= responseCode && responseCode <= 399;
        } catch (IOException exception) {
            return false;
        } finally {
            if( Objects.nonNull( connection ) ) connection.disconnect();
        }
    }
}
