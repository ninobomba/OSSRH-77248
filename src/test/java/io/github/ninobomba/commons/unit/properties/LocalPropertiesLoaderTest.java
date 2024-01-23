package io.github.ninobomba.commons.unit.properties;

import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LocalPropertiesLoaderTest {
	
	@Test
	void instanceTest ( ) {
		var instance = LocalPropertiesLoader.getInstance ( );
		assertThat ( instance ).isNotNull ( );
	}
	
	@Test
	void getEmptyPropertyTest ( ) {
		var property = LocalPropertiesLoader.getInstance ( ).getProperty ( "noProperty" );
		assert property == null;
	}
	
	@Test
	void getValidPropertyTest ( ) {
		var property = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.application.id" );
		System.out.println ( property );
		assertThat ( property ).isNotNull ( );
	}
	
}
