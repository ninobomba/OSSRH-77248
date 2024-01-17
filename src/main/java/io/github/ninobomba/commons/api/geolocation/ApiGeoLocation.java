package io.github.ninobomba.commons.api.geolocation;

import io.github.ninobomba.commons.exceptions.core.messages.LocalExceptionMessage;
import io.github.ninobomba.commons.exceptions.types.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public interface ApiGeoLocation {
	
	/**
	 * Retrieves the geo location information for the given IP address.
	 *
	 * @param ip The IP address for which to retrieve the geo location information.
	 * @return The geo location information for the given IP address.
	 * @throws EmptyOrNullParameterException if the IP address, access key, or base URL is null or empty.
	 */
	@SneakyThrows
	static String getGeoLocationByIp ( String ip ) {
		
		if ( StringUtils.isBlank ( ip ) )
			throw LocalExceptionMessage.builder ( )
					.message ( "ApiGeoLocation: getGeoLocationInfoByIpAddress() !: empty or null ip address: " + ip )
					.build ( )
					.create ( EmptyOrNullParameterException.class );
		
		val accessKey = LocalPropertiesLoader.getInstance ( ).getProperty ( "api.geolocation.key" );
		if ( StringUtils.isBlank ( accessKey ) )
			throw LocalExceptionMessage.builder ( )
					.message ( "ApiGeoLocation: getGeoLocationInfoByIpAddress() !: accessKey is null or empty: " + accessKey )
					.build ( )
					.create ( EmptyOrNullParameterException.class );
		
		val baseUrl = LocalPropertiesLoader.getInstance ( ).getProperty ( "api.geolocation.url" );
		if ( StringUtils.isBlank ( baseUrl ) )
			throw LocalExceptionMessage.builder ( )
					.message ( "ApiGeoLocation: getGeoLocationInfoByIpAddress() !: baseUrl is null or empty: " + baseUrl )
					.build ( )
					.create ( EmptyOrNullParameterException.class );
		
		val url = baseUrl + "/" + ip + "?access_key=" + accessKey;
		
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
