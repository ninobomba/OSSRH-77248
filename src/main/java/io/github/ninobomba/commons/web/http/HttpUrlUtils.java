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


/**
 * This interface provides utility methods for working with HTTP URLs.
 */
public interface HttpUrlUtils {
	
	/**
	 * Builds a URL object from the given path.
	 *
	 * @param path The path to be used for building the URL.
	 * @return The URL object built from the given path.
	 * @throws URISyntaxException If the given path is not a valid URI.
	 */
	@SneakyThrows
	static URL buildURL ( String path ) {
		return new URI ( path ).toURL ( );
	}
	
	/**
	 * Builds a URI object for the given path.
	 *
	 * @param path The path to be used for building the URI.
	 * @return The URI object built from the given path.
	 */
	@SneakyThrows
	static URI buildURI ( String path ) {
		return new URI ( path );
	}
	
	/**
	 * Parses a URL with parameters and returns a map containing the various components of the URL.
	 *
	 * @param uri the URL string to be parsed
	 * @return a map containing the components of the parsed URL
	 * @throws URISyntaxException if the given URL string is invalid
	 */
	@SneakyThrows
	static Map < String, String > parseUrlWithParameters ( String uri ) {
		var response = new HashMap < String, String > ( );
		
		if ( StringUtils.isBlank ( uri ) ) return response;
		
		var url = new URI ( uri ).toURL ( );
		
		response.put ( "authority", url.getAuthority ( ) );
		response.put ( "protocol", url.getProtocol ( ) );
		response.put ( "host", url.getHost ( ) );
		response.put ( "port", String.valueOf ( url.getPort ( ) ) );
		response.put ( "path", url.getPath ( ) );
		response.put ( "query", url.getQuery ( ) );
		response.put ( "filename", url.getFile ( ) );
		response.put ( "ref", url.getRef ( ) );
		
		return response;
	}
	
	/**
	 * Checks if a host is available by sending a HEAD request to the specified URL.
	 * This method uses default HTTP port 80 and a timeout of 10,000 milliseconds.
	 *
	 * @param url     the URL of the host to check availability for
	 * @return true if the host is available and responds with a successful HTTP status code (between 200 and 399);
	 *         false otherwise or if an exception occurs during the check
	 */
	static boolean hostAvailabilityCheck ( String url ) {
		return hostAvailabilityCheck ( url, 0, 10_000 );
	}
	
	/**
	 * Checks the availability of a host by sending a HEAD request to the specified URL and port.
	 *
	 * @param url     the URL of the host (e.g., "http://example.com")
	 * @param port    the port number of the host (0 indicates the default port)
	 * @param timeout the timeout value in milliseconds for the connection and read operations
	 * @return true if the host is available and returns a successful response code (2xx), false otherwise
	 * @throws URISyntaxException if the URL is not valid
	 */
	static boolean hostAvailabilityCheck ( String url, int port ) {
		return hostAvailabilityCheck ( url, port, 10_000 );
	}
	
	@SneakyThrows
	static boolean hostAvailabilityCheck ( String url, int port, int timeout ) {
		url = url.replaceFirst ( "^https", "http" ); // Otherwise, an exception may be thrown on invalid SSL certificates.
		
		HttpURLConnection connection = null;
		try {
			String uri = port > 0 ? url.concat ( ":" + port ) : url;
			connection = ( HttpURLConnection ) new URI ( uri ).toURL ( ).openConnection ( );
			connection.setConnectTimeout ( timeout );
			connection.setReadTimeout ( timeout );
			connection.setRequestMethod ( "HEAD" );
			int responseCode = connection.getResponseCode ( );
			return 200 <= responseCode && responseCode <= 399;
		} catch ( IOException exception ) {
			return false;
		} finally {
			if ( Objects.nonNull ( connection ) ) connection.disconnect ( );
		}
	}
}
