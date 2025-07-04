package io.github.ninobomba.commons.unit.web.http;

import io.github.ninobomba.commons.web.http.javax.HttpResponseDataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

class HttpResponseDataUtilsTest {

	@Test
	void getResponseHeadersMapTest ( ) {
		MockHttpServletResponse servletResponse = new MockHttpServletResponse ( );
		servletResponse.addHeader ( "key-1", "value-1" );
		servletResponse.addHeader ( "key-2", "value-2" );
		servletResponse.addHeader ( "key-3", "value-3" );
		var map = HttpResponseDataUtils.getResponseHeadersMap ( servletResponse );
		assert ( ! map.isEmpty ( ) );
		map.forEach ( ( k, v ) -> System.out.println ( k + " : " + v ) );
	}

	// https://www.baeldung.com/java-httpservletrequest-mock

}
