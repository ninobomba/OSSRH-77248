package io.github.ninobomba.commons.unit.request;

import io.github.ninobomba.commons.json.JsonUtils;
import io.github.ninobomba.commons.request.Request;
import org.junit.jupiter.api.Assertions;

class RequestTest {
	
	//@Test
	void pushEventTest ( ) {
		
		Request request = Request.builder ( )
				.name ( getClass ( ).getName ( ) )
				.payload ( "{}" )
				.build ( );
		
		request.pushEvent ( "start" );
		request.pushEvent ( "processing" );
		request.pushEvent ( "complete" );
		
		var json = request.toJsonString ( true );
		System.out.println ( json );
		
		Assertions.assertTrue ( JsonUtils.isValidJson ( json ) );
	}
	
}
