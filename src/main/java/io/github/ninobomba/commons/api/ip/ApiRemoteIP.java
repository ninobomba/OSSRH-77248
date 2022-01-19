package io.github.ninobomba.commons.api.ip;

import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@UtilityClass
public class ApiRemoteIP
{

    @SneakyThrows
    public static String getRemoteIpByAws()
    {
        String awsUrl = LocalPropertiesLoader.getInstance().getProperty( "api.remote.ip.aws" );

        if( StringUtils.isBlank( awsUrl ) )
            throw EmptyOrNullParameterException.create( "HttpRemoteIpTools: getRemoteIpByAws() !: aws url is blank", awsUrl );

        String response;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL( awsUrl ).openStream()))) {
            response = br.readLine();
        }

        return response;
    }

}
