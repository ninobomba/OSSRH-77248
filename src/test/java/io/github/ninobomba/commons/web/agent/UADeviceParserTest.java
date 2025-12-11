package io.github.ninobomba.commons.web.agent;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith ( MockitoExtension.class )
class UADeviceParserTest {

	@Mock
	private HttpServletRequest jakartaRequest;

	@Mock
	private javax.servlet.http.HttpServletRequest javaxRequest;

	@Test
	void getDeviceDetails_shouldThrowNullPointerException_whenRequestIsNull ( ) {
		assertThrows ( NullPointerException.class, ( ) -> UADeviceParser.getDeviceDetails ( null ) );
	}

	@Test
	void getDeviceDetails_shouldThrowIllegalArgumentException_whenRequestIsUnsupported ( ) {
		assertThrows ( IllegalArgumentException.class, ( ) -> UADeviceParser.getDeviceDetails ( "invalid" ) );
	}

	@Test
	void getDeviceDetails_shouldReturnDetails_whenJakartaRequestIsValid ( ) {
		when ( jakartaRequest.getParameter ( "vxp_uid" ) ).thenReturn ( "123" );
		when ( jakartaRequest.getParameter ( "vxp_token" ) ).thenReturn ( "token" );
		when ( jakartaRequest.getParameter ( "vxp_username" ) ).thenReturn ( "user" );
		when ( jakartaRequest.getParameter ( "vxp_ua_timezone" ) ).thenReturn ( "UTC" );
		when ( jakartaRequest.getHeader ( "User-Agent" ) ).thenReturn ( "Mozilla/5.0" );
		
		UADevice device = UADeviceParser.getDeviceDetails ( jakartaRequest );

		assertNotNull ( device );
		assertEquals ( "123", device.id ( ) );
		assertEquals ( "user", device.username ( ) );
	}

	@Test
	void getDeviceDetails_shouldReturnDetails_whenJavaxRequestIsValid ( ) {
		when ( javaxRequest.getParameter ( "vxp_uid" ) ).thenReturn ( "123" );
		when ( javaxRequest.getParameter ( "vxp_token" ) ).thenReturn ( "token" );
		when ( javaxRequest.getParameter ( "vxp_username" ) ).thenReturn ( "user" );
		when ( javaxRequest.getParameter ( "vxp_ua_timezone" ) ).thenReturn ( "UTC" );
		when ( javaxRequest.getHeader ( "User-Agent" ) ).thenReturn ( "Mozilla/5.0" );

		UADevice device = UADeviceParser.getDeviceDetails ( javaxRequest );

		assertNotNull ( device );
		assertEquals ( "123", device.id ( ) );
	}
}
