package io.github.ninobomba.commons.unit.request;

import io.github.ninobomba.commons.checkpoints.CheckPointFactory;
import io.github.ninobomba.commons.request.Request;
import io.github.ninobomba.commons.request.RequestManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RequestManagerTest {
	
	static Request request = Request
			.builder ( )
			.name ( "RequestManagerTest" )
			.payload ( "{}" )
			.build ( );
	
	@BeforeAll
	public static void setUp ( ) {
		var paths = new ArrayList < String > ( );
		paths.add ( "src/main/resources/checkpoint/signup.json" );
		CheckPointFactory.getInstance ( ).build ( paths );
		request.createCheckPointMapWithKey ( "signup" );
	}
	
	@SneakyThrows
	//@Test
	void addRequestTest ( ) {
		// events
		System.out.println ( "Working events" );
		int a = 0;
		while ( a++ < 5 ) {
			request.pushEvent ( String.valueOf ( a ) );
		}
		
		//checkpoints
		System.out.println ( "Working checkpoints" );
		var checkpointMap = request.getCheckPointMap ( );
		checkpointMap.forEach ( ( k, v ) -> v.update ( ) );
		
		RequestManager.getInstance ( ).add ( request );
		TimeUnit.MILLISECONDS.sleep ( 1_000 );
		
		RequestManager.checkOnQueue ( );
	}
	
}
