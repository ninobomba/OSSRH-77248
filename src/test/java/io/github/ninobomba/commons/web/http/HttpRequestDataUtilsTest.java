package io.github.ninobomba.commons.web.http;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class HttpRequestDataUtilsTest {



    @Test
    void getRequestParametersMapTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");

        var map = HttpRequestDataUtils.getRequestParametersMap( request );

        assert( ! map.isEmpty() );

        map.forEach( (k,v) -> System.out.println( k + " : " + v ) );
    }

    @Test
    void getRequestHeadersMapTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");

        request.addHeader( "h1", "v1" );
        request.addHeader( "h2", "v2" );
        request.addHeader( "h3", "v3" );

        var map = HttpRequestDataUtils.getRequestHeadersMap( request );

        assert( ! map.isEmpty() );

        map.forEach( (k,v) -> System.out.println( k + " : " + v ) );

    }


}
