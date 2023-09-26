package io.github.ninobomba.commons.api.geolocation;

import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
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

public interface ApiGeoLocation
{

    @SneakyThrows
    static String getGeoLocationByStackIp( String ip )
    {

        if( StringUtils.isBlank( ip ) )
            throw EmptyOrNullParameterException.create( "ApiGeoLocation: getGeoLocationInfoByIpAddress() !: empty or null ip address", ip );

        val accessKey = LocalPropertiesLoader.getInstance().getProperty( "api.geolocation.key" );
        if( StringUtils.isBlank( accessKey ) )
            throw EmptyOrNullParameterException.create("ApiGeoLocation::getGeoLocationInfo() !: accessKey is null or empty", accessKey );

        val baseUrl = LocalPropertiesLoader.getInstance().getProperty( "api.geolocation.url" );
        if( StringUtils.isBlank( baseUrl ) )
            throw EmptyOrNullParameterException.create( "ApiGeoLocation: getGeoLocationInfo() !: baseUrl is null or empty", baseUrl );

        val url = baseUrl
                .concat( "/" )
                .concat( ip )
                .concat( "?access_key=" )
                .concat( accessKey );

        val connection = ( HttpURLConnection ) new URI( url ).toURL().openConnection();
        connection.setRequestMethod( "GET" );
        connection.setRequestProperty( "User-Agent", "Mozilla/5.0" );

        var builder = new StringBuilder();
        try( var reader = new BufferedReader( new InputStreamReader( connection.getInputStream(), StandardCharsets.UTF_8 ) ) ) {
            String inputLine;
            while ( Objects.nonNull( inputLine = reader.readLine()) ) builder.append( inputLine );
        }

        return builder.toString();
    }

}
