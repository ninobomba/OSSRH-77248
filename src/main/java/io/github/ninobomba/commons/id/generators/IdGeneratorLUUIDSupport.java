package io.github.ninobomba.commons.id.generators;

import de.huxhorn.sulky.ulid.ULID;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public final class IdGeneratorLUUIDSupport {

	private static final ConcurrentLinkedQueue < String > queue = new ConcurrentLinkedQueue <> ( );

	private static volatile IdGeneratorLUUIDSupport INSTANCE;

	private static final ULID ulidGen = new ULID ( );

	private static final int MAX_QUEUE_SIZE = 10_000;
	private static final int MIN_QUEUE_SIZE_BEFORE_LOAD = 10;

	private IdGeneratorLUUIDSupport ( ) {
		load ( );
	}

	/**
	 * Returns the singleton instance of IdGeneratorLUUIDSupport.
	 *
	 * @return the instance of IdGeneratorLUUIDSupport
	 */
	public static IdGeneratorLUUIDSupport getInstance ( ) {
		if ( INSTANCE == null ) {
			synchronized ( IdGeneratorLUUIDSupport.class ) {
				if ( INSTANCE == null ) {
					INSTANCE = new IdGeneratorLUUIDSupport ( );
				}
			}
		}
		return INSTANCE;

	}

	public String getNextId ( ) {
		if ( queue.size ( ) <= MIN_QUEUE_SIZE_BEFORE_LOAD ) {
			load ( );
		}
		String id = queue.poll ( );
		return id != null ? id : generateId ( );
	}

	private static void load ( ) {
		synchronized ( IdGeneratorLUUIDSupport.class ) {
			int freeSlots = Math.max ( 0, MAX_QUEUE_SIZE - queue.size ( ) );
			if ( freeSlots == 0 ) return;
			int batch = Math.min ( freeSlots, MAX_QUEUE_SIZE / 2 );
			Stream
					.generate ( IdGeneratorLUUIDSupport::generateId )
					.limit ( batch )
					.forEach ( queue::offer );
		}
	}

	private static String generateId ( ) {
		return ulidGen.nextULID ( );
	}
}
