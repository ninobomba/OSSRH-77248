package io.github.ninobomba.commons.unit.web.http;

import io.github.ninobomba.commons.web.http.javax.HttpRequestDataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

class HttpRequestDataUtilsTest {

	@Test
	void getRequestParametersMapTest ( ) {
		MockHttpServletRequest request = new MockHttpServletRequest ( );
		request.setParameter ( "firstName", "Spring" );
		request.setParameter ( "lastName", "Test" );

		var map = HttpRequestDataUtils.getRequestParametersMap ( request );

		assert ( ! map.isEmpty ( ) );

		map.forEach ( ( k, v ) -> System.out.println ( k + " : " + v ) );
	}

	@Test
	void getRequestHeadersMapTest ( ) {
		MockHttpServletRequest request = new MockHttpServletRequest ( );
		request.setParameter ( "firstName", "Spring" );
		request.setParameter ( "lastName", "Test" );

		request.addHeader ( "h1", "v1" );
		request.addHeader ( "h2", "v2" );
		request.addHeader ( "h3", "v3" );

		var map = HttpRequestDataUtils.getRequestHeadersMap ( request );

		assert ( ! map.isEmpty ( ) );

		map.forEach ( ( k, v ) -> System.out.println ( k + " : " + v ) );

	}


	@Test
	void test1 ( ) {

		var evenList = List.of ( 1, 2, 3, 4, 5, 6, 7, 8, 9 ).stream ( ).filter ( e -> e % 2 == 0 ).toList ( );
		evenList.forEach ( System.out::println );

		String tenet = "tenet";
		assert ( new StringBuilder ( tenet ).reverse ( ).toString ( ).equals ( tenet ) );
	}


	class Country {

		private String name;

		public Country ( String name ) {
			this.name = name;
		}

	}

}
