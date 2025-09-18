package io.github.ninobomba.commons.id;

import io.github.ninobomba.commons.id.generators.IdGeneratorConcurrentLinkedQueueSupport;
import io.github.ninobomba.commons.id.generators.IdGeneratorHashSetSupport;
import io.github.ninobomba.commons.id.generators.IdGeneratorLUUIDSupport;
import io.github.ninobomba.commons.id.generators.IdGeneratorSnowFlakeSupport;

public enum IdGenerator {

	INSTANCE;

	private IdGeneratorConcurrentLinkedQueueSupport idGeneratorConcurrentLinkedQueueSupport;
	private IdGeneratorHashSetSupport idGeneratorHashSetSupport;
	private IdGeneratorSnowFlakeSupport idGeneratorSnowFlakeSupport;
	private IdGeneratorLUUIDSupport idGeneratorLUUIDSupport;

	private long getNextIdConcurrentLinkedQueueSupport ( ) {
		if ( idGeneratorConcurrentLinkedQueueSupport == null )
			idGeneratorConcurrentLinkedQueueSupport = IdGeneratorConcurrentLinkedQueueSupport.getINSTANCE ( );
		return idGeneratorConcurrentLinkedQueueSupport.getNextId ( );
	}

	private long getNextIdHashSetSupport ( ) {
		if ( idGeneratorHashSetSupport == null )
			idGeneratorHashSetSupport = IdGeneratorHashSetSupport.getINSTANCE ( );
		return idGeneratorHashSetSupport.getNextId ( );
	}

	private long getNextIdSnowFlakeSupport ( ) {
		if ( idGeneratorSnowFlakeSupport == null )
			idGeneratorSnowFlakeSupport = IdGeneratorSnowFlakeSupport.getINSTANCE ( );
		return idGeneratorSnowFlakeSupport.getNextId ( );
	}

	public String getNextIdLUUID ( ) {
		if ( idGeneratorLUUIDSupport == null ) idGeneratorLUUIDSupport = IdGeneratorLUUIDSupport.getInstance ( );
		return idGeneratorLUUIDSupport.getNextId ( );
	}

	public String getNextIdAsUUID ( IdGeneratorUUIDSupport.TYPE uuidType ) {
		return IdGeneratorUUIDSupport.INSTANCE.getNextId ( uuidType );
	}

	public long getNextId ( TYPE type ) {
		return switch ( type ) {
			case CONCURRENT_LINKED_QUEUE -> getNextIdConcurrentLinkedQueueSupport ( );
			case HASH_SET -> getNextIdHashSetSupport ( );
			case SNOW_FLAKE -> getNextIdSnowFlakeSupport ( );
			default -> throw new UnsupportedOperationException ( "Unsupported id type: " + type );
		};
	}

	public enum TYPE {
		CONCURRENT_LINKED_QUEUE, HASH_SET, SNOW_FLAKE, LUUID, UUID;
	}

}
