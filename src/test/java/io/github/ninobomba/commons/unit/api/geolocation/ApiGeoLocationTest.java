package io.github.ninobomba.commons.unit.api.geolocation;

import io.github.ninobomba.commons.api.geolocation.ApiGeoLocation;
import io.github.ninobomba.commons.exceptions.types.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.web.http.javax.HttpRemoteIpUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class ApiGeoLocationTest {
	@Test
	void getGeoLocationInfoByIpAddressTest ( ) {
		var request = Mockito.mock ( HttpServletRequest.class );
		var ip = HttpRemoteIpUtils.getRemoteIpByHttpRequestHeaders ( request );
		System.out.println ( "getRemoteIpByHttpRequestHeaders() > ip value is: " + ip );
		assertThat ( ip ).isNull ( );
		Assertions.assertThrows ( EmptyOrNullParameterException.class, ( ) -> {
			var geo = ApiGeoLocation.getGeoLocationByIp ( null );
		} );
	}

	@Test
	void getGeoLocationByIpAddressTest ( ) {
		var ip = "189.128.121.26";
		var geo = ApiGeoLocation.getGeoLocationByIp ( ip );
		assertThat ( geo ).isNotBlank ( );
		System.out.println ( "ip: " + ip + " geo: " + geo );
	}
}
