package io.github.ninobomba.commons.api.geolocation;

import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
import lombok.extern.slf4j.Slf4j;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Slf4j
@UtilityClass
public final class ApiGeoLocation
{

    @SneakyThrows
    public String getGeoLocationByStackIp( String ip )
    {
        log.trace( "ApiGeoLocation::getGeoLocationInfoByIpAddress() >: start" );

        if( StringUtils.isBlank( ip ) )
            throw EmptyOrNullParameterException.create( "ApiGeoLocation: getGeoLocationInfoByIpAddress() !: empty or null ip address", ip );

        var accessKey = LocalPropertiesLoader.getInstance().getProperty( "api.geolocation.key" );
        if( StringUtils.isBlank( accessKey ) )
            throw EmptyOrNullParameterException.create("ApiGeoLocation::getGeoLocationInfo() !: accessKey is null or empty", accessKey );

        var baseUrl = LocalPropertiesLoader.getInstance().getProperty( "api.geolocation.url" );
        if( StringUtils.isBlank( baseUrl ) )
            throw EmptyOrNullParameterException.create( "ApiGeoLocation: getGeoLocationInfo() !: baseUrl is null or empty", baseUrl );

        String url = baseUrl
                .concat( "/" )
                .concat( ip )
                .concat( "?access_key=" )
                .concat( accessKey );

        log.debug( "ApiGeoLocation: getGeoLocationInfo() _: requesting geolocation {}", url );

        var connection = (HttpURLConnection) new URL( url ).openConnection();
        connection.setRequestMethod( "GET" );
        connection.setRequestProperty( "User-Agent", "Mozilla/5.0" );

        StringBuilder builder = new StringBuilder();
        try( var reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) ) ) {
            String inputLine;
            while ( Objects.nonNull( inputLine = reader.readLine()) ) builder.append( inputLine );
        }

        var response = builder.toString();

        log.debug( "ApiGeoLocation: getGeoLocationInfoByIpAddress() _: geolocation response {}", response );

        log.trace( "ApiGeoLocation: getGeoLocationInfoByIpAddress() <: complete" );

        return response;
    }

}
