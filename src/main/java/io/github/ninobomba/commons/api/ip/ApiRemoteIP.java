package io.github.ninobomba.commons.api.ip;

import io.github.ninobomba.commons.exceptions.core.messages.LocalExceptionMessageBuilder;
import io.github.ninobomba.commons.exceptions.types.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 *
 */
public interface ApiRemoteIP {

	/**
	 * Retrieves the remote IP address using AWS API.
	 *
	 * @return The remote IP address.
	 * @throws EmptyOrNullParameterException If the AWS URL is blank.
	 */
	@SneakyThrows
	static String getRemoteIpUsingAwsService ( ) {
		String uri = LocalPropertiesLoader.getInstance ( ).getProperty ( "api.remote.ip.aws" );

		if ( StringUtils.isBlank ( uri ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "HttpRemoteIpTools: getRemoteIpUsingAwsService() !:  url is blank: " + uri )
					.build ( )
					.create ( EmptyOrNullParameterException.class );

		try ( var br = new BufferedReader ( new InputStreamReader ( new URI ( uri ).toURL ( ).openStream ( ), StandardCharsets.UTF_8 ) ) ) {
			return br.readLine ( );
		}

	}

	/**
	 * Retrieves the remote IP address using the Apify service.
	 *
	 * @return The remote IP address.
	 * @throws EmptyOrNullParameterException If the URL is blank.
	 */
	@SneakyThrows
	static String getRemoteIpUsingApifyService ( ) {
		String uri = LocalPropertiesLoader.getInstance ( ).getProperty ( "api.remote.ip.apify" );

		if ( StringUtils.isBlank ( uri ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "HttpRemoteIpTools: getRemoteIpUsingApifyService() !:  url is blank: " + uri )
					.build ( )
					.create ( EmptyOrNullParameterException.class );

		HttpRequest request = HttpRequest.newBuilder ( ).uri ( URI.create ( uri ) ).build ( );

		try ( var client = HttpClient.newHttpClient ( ) ) {
			return client.send ( request, HttpResponse.BodyHandlers.ofString ( ) ).body ( );
		}
	}
	
}
