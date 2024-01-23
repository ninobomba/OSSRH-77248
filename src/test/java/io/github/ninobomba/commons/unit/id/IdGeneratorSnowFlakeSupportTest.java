package io.github.ninobomba.commons.unit.id;

import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;

class IdGeneratorSnowFlakeSupportTest {
	
//	@Test
	void getNextIdTest ( ) {
		var id = IdGeneratorSnowFlakeSupport.getInstance ( ).getNextId ( );
		System.out.println ( "IdGenerator: getNextId(): " + id );
		assert ( id > 0 );
	}
	
	
}
