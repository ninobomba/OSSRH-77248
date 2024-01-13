package io.github.ninobomba.commons.api.ip;

import io.github.ninobomba.commons.exceptions.core.messages.LocalExceptionMessage;
import io.github.ninobomba.commons.exceptions.types.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public interface ApiRemoteIP {

    /**
     * Retrieves the remote IP address using AWS API.
     *
     * @return The remote IP address.
     * @throws EmptyOrNullParameterException If the AWS URL is blank.
     * @throws IOException                   If an I/O error occurs when retrieving the remote IP address.
     * @throws URISyntaxException            If the AWS URL is not a valid URI.
     */
    @SneakyThrows
    static String getRemoteIpByAws() {
        String awsUrl = LocalPropertiesLoader.getInstance().getProperty("api.remote.ip.aws");

        if (StringUtils.isBlank(awsUrl))
            throw LocalExceptionMessage.builder()
                    .message("HttpRemoteIpTools: getRemoteIpByAws() !: aws url is blank: " + awsUrl)
                    .tClass(EmptyOrNullParameterException.class)
                    .build()
                    .create();

        try (var br = new BufferedReader(new InputStreamReader(new URI(awsUrl).toURL().openStream(), StandardCharsets.UTF_8))) {
            return br.readLine();
        }

    }

}
