package io.github.ninobomba.commons.api.geolocation;

import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.web.http.HttpRemoteIpUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class ApiGeoLocationTest
{
    @Test
    void getGeoLocationInfoByIpAddressTest() {

        var request = Mockito.mock( HttpServletRequest.class );

        var ip = HttpRemoteIpUtils.getRemoteIpByHttpRequestHeaders( request );

        System.out.println( "getRemoteIpByHttpRequestHeaders() > ip value is: " +  ip );

        assertThat( ip ).isNull();

        Assertions.assertThrows(EmptyOrNullParameterException.class, () -> {
            var geo= ApiGeoLocation.getGeoLocationByIp( null );
        });

    }

}
