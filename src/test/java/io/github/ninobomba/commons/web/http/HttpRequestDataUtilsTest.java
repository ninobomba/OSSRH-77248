package io.github.ninobomba.commons.web.http;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

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



    @Test
    void test1() {

        java.util.List<Integer> list = List.of( 1,2,3,4,5,6,7,8,9);

        list.stream().filter( e -> e % 2 == 0 ).collect( Collectors.toList() );

        String tenet = "tenet";

        new StringBuilder( tenet ).reverse().equals( tenet );

    }


    class Country  {

        private String name;

        public Country(String name) {
            this.name = name;
        }

    }

}
