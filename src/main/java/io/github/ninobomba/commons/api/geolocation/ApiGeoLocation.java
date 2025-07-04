package io.github.ninobomba.commons.api.geolocation;

import io.github.ninobomba.commons.exceptions.core.messages.LocalExceptionMessageBuilder;
import io.github.ninobomba.commons.exceptions.types.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * The ApiGeoLocation interface provides methods for retrieving geolocation information for a given IP address.
 */
public interface ApiGeoLocation {
	/**
	 * Retrieves the geolocation information for the given IP address.
	 *
	 * @param ip The IP address for which to retrieve the geolocation information.
	 * @return The geolocation information for the given IP address.
	 * @throws EmptyOrNullParameterException if the IP address, access key, or base URL is null or empty.
	 */
	@SneakyThrows
	static String getGeoLocationByIp ( String ip ) {
		var url = formatIpAddress ( ip );
		return fetchGeoLocation ( url );
	}

	/**
	 * Retrieves the geolocation information from the specified URL.
	 *
	 * @param url The URL from which to fetch the geolocation information.
	 * @return The geolocation information returned from the URL.
	 */
	@SneakyThrows
	static String fetchGeoLocation ( String url ) {
		try ( var httpClient = HttpClient.newBuilder ( )
				.version ( HttpClient.Version.HTTP_1_1 )
				.connectTimeout ( Duration.ofSeconds ( 5 ) )
				.build ( ) ) {
			var request = HttpRequest.newBuilder ( )
					.GET ( )
					.uri ( URI.create ( url ) )
					.build ( );

			return httpClient.sendAsync ( request, HttpResponse.BodyHandlers.ofString ( ) )
					.thenApply ( HttpResponse::body )
					.get ( 5, TimeUnit.SECONDS );
		}
	}

	/**
	 * Formats an IP address into a specific URL format for geolocation retrieval.
	 *
	 * @param ip The IP address to format.
	 * @return The formatted URL for geolocation retrieval.
	 * @throws EmptyOrNullParameterException if the IP address, access key, or base URL is null or empty.
	 */
	static String formatIpAddress ( String ip ) {
		validateParameter ( ip, "empty or null ip address" );
		String accessKey = getProperty ( "api.geolocation.key", "accessKey is null or empty" );
		String baseUrl = getProperty ( "api.geolocation.url", "baseUrl is null or empty" );

		return String.format ( "%s/%s?access_key=%s", baseUrl, ip, accessKey );
	}

	/**
	 * Validates a parameter to ensure it is not blank or null.
	 *
	 * @param parameter    The parameter to be validated.
	 * @param errorMessage The error message to be used if the parameter is blank or null.
	 * @throws EmptyOrNullParameterException if the parameter is blank or null.
	 */
	static void validateParameter ( String parameter, String errorMessage ) {
		if ( StringUtils.isBlank ( parameter ) ) {
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "ApiGeoLocation: " + errorMessage + ": " + parameter )
					.build ( )
					.create ( EmptyOrNullParameterException.class );
		}
	}

	/**
	 * Retrieves the value of a property with the given key from the local properties file.
	 *
	 * @param key          The key of the property to retrieve.
	 * @param errorMessage The error message to include in the exception if the property is null or empty.
	 * @return The value of the property as a String.
	 * @throws EmptyOrNullParameterException If the property is null or empty.
	 */
	static String getProperty ( String key, String errorMessage ) {
		String property = LocalPropertiesLoader.getInstance ( ).getProperty ( key );
		validateParameter ( property, errorMessage );
		return property;
	}


	/**
	 * Retrieves the content of a URL using HTTP connection.
	 *
	 * @param url The URL from which to retrieve the content.
	 * @return The content of the URL.
	 */
	@SneakyThrows
	static String getUsingHttpConnection ( String url ) {
		HttpURLConnection connection = null;
		var builder = new StringBuilder ( );

		try {
			connection = ( HttpURLConnection ) new URI ( url ).toURL ( ).openConnection ( );
			connection.setRequestMethod ( "GET" );
			connection.setRequestProperty ( "User-Agent", "Mozilla/5.0" );

			try ( var reader = new BufferedReader ( new InputStreamReader ( connection.getInputStream ( ), StandardCharsets.UTF_8 ) ) ) {
				String inputLine;
				while ( Objects.nonNull ( inputLine = reader.readLine ( ) ) ) builder.append ( inputLine );
			}

		} finally {
			if ( Objects.nonNull ( connection ) )
				connection.disconnect ( );
		}

		return builder.toString ( );
	}
}
