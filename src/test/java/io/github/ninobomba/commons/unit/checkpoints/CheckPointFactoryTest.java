package io.github.ninobomba.commons.unit.checkpoints;

import io.github.ninobomba.commons.checkpoints.CheckPointFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Tag ( "CheckPointTests" )
class CheckPointFactoryTest {
	
	@Test
	@Order ( 1 )
	@DisplayName ( "buildTest" )
	void buildTest ( ) {
		List < String > paths = new ArrayList <> ( );
		paths.add ( "checkpoints/signup.json" );
		assertDoesNotThrow ( ( ) -> CheckPointFactory.getInstance ( ).build ( paths ) );
	}
	
	@Test
	@Order ( 2 )
	@DisplayName ( "getCheckPointTest" )
	void getCheckPointTest ( ) {
		List < String > paths = new ArrayList <> ( );
		paths.add ( "checkpoints/signup.json" );
		CheckPointFactory.getInstance ( ).build ( paths );
		var map = CheckPointFactory.getInstance ( ).getCheckPointMap ( "signup" );
		assertThat ( map ).isNotEmpty ( );
		map.forEach ( ( k, v ) -> System.out.println ( k + ":" + v ) );
	}
	
	
	@Test
	@Order ( 3 )
	@DisplayName ( "checkPointUpdateTest" )
	void checkPointUpdateTest ( ) {
		List < String > paths = new ArrayList <> ( );
		
		paths.add ( "checkpoints/signup.json" );
		CheckPointFactory.getInstance ( ).build ( paths );
		
		var map = CheckPointFactory.getInstance ( ).getCheckPointMap ( "signup" );
		assertThat ( map ).isNotEmpty ( );
		
		System.out.println ( );
		
		assert map != null;
		
		map.forEach ( ( k, v ) -> {
			
			System.out.println ( "Before: " + k + ":" + v );
			
			assertThat ( v.getCompleted ( ) ).isNull ( );
			assertThat ( v.getLocalDateTime ( ) ).isNull ( );
			assertThat ( v.getFormattedTimestamp ( ) ).isNull ( );
			
			v.update ( );
			System.out.println ( "After: " + k + ":" + v );
			
			assertThat ( v.getCompleted ( ) ).isTrue ( );
			assertThat ( v.getLocalDateTime ( ) ).isNotNull ( );
			assertThat ( v.getFormattedTimestamp ( ) ).isNotNull ( );
			
			System.out.println ( );
		} );
		
		System.out.println ( "::::::::::::::::" );
		System.out.println ( "Template Summary" );
		System.out.println ( CheckPointFactory.getInstance ( ).templateSummary ( "signup" ) );
	}
	
}
