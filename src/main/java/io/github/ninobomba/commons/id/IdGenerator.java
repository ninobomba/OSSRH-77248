package io.github.ninobomba.commons.id;

public enum IdGenerator {

	INSTANCE;

	private IdGeneratorConcurrentLinkedQueueSupport idGeneratorConcurrentLinkedQueueSupport;
	private IdGeneratorHashSetSupport idGeneratorHashSetSupport;
	private IdGeneratorSnowFlakeSupport idGeneratorSnowFlakeSupport;

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

	public long getNextId ( TYPE type ) {
		return switch ( type ) {
			case CONCURRENT_LINKED_QUEUE -> getNextIdConcurrentLinkedQueueSupport ( );
			case HASH_SET -> getNextIdHashSetSupport ( );
			case SNOW_FLAKE -> getNextIdSnowFlakeSupport ( );
		};
	}

	public String getNextIdAsUUID ( IdGeneratorUUIDSupport.TYPE uuidType ) {
		return IdGeneratorUUIDSupport.INSTANCE.getNextId ( uuidType );
	}

	public enum TYPE {
		CONCURRENT_LINKED_QUEUE, HASH_SET, SNOW_FLAKE;
	}

}
