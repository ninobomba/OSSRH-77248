package io.github.ninobomba.commons.unit.id;

import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;

class IdGeneratorSnowFlakeSupportTest {

	//	@Test
	void getNextIdTest ( ) {
		var id = IdGeneratorSnowFlakeSupport.getINSTANCE ( ).getNextId ( );
		System.out.println ( "IdGeneratorConcurrentLinkedQueueSupport: getNextId(): " + id );
		assert ( id > 0 );
	}


}
