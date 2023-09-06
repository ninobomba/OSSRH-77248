package io.github.ninobomba.commons.api.ip;

import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public interface ApiRemoteIP
{

    @SneakyThrows
    static String getRemoteIpByAws()
    {
        String awsUrl = LocalPropertiesLoader.getInstance().getProperty( "api.remote.ip.aws" );

        if( StringUtils.isBlank( awsUrl ) )
            throw EmptyOrNullParameterException.create( "HttpRemoteIpTools: getRemoteIpByAws() !: aws url is blank", awsUrl );

        String response;
        try (BufferedReader br = new BufferedReader( new InputStreamReader( new URI( awsUrl ).toURL().openStream(), StandardCharsets.UTF_8))) {
            response = br.readLine();
        }

        return response;
    }

}
