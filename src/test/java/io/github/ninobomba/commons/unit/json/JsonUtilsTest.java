package io.github.ninobomba.commons.unit.json;

import io.github.ninobomba.commons.json.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonUtilsTest {
	
	@Test
	void isValidJsonTest ( ) {
		var isValidJson = JsonUtils.isValidJson ( "{}" );
		assert ( isValidJson );
	}
	
	@Test
	void prettyTest ( ) {
		var prettyJson = JsonUtils.pretty ( "{\"phone\":\"4158149716\",\"mobile\":\"4425626535\"}" );
		assertNotNull ( prettyJson );
		System.out.println ( prettyJson );
	}
	
}
