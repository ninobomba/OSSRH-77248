package io.github.ninobomba.commons.id.generators;

import lombok.extern.slf4j.Slf4j;
import xyz.downgoon.snowflake.Snowflake;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The IdGeneratorSnowFlakeSupport class provides support for generating unique id numbers using the Snowflake algorithm.
 * It maintains a queue of id numbers to ensure fast and efficient generation of new ids.
 * The loading process is parallelized to maximize performance.
 * <p>
 * Usage Example:
 * <p>
 * To generate a new id number, call the getNextId() method:
 * <pre>
 * {@code
 * long id = IdGeneratorSnowFlakeSupport.getInstance().getNextId();
 * }
 * </pre>
 * <p>
 * To load new id numbers into memory, call the load() method:
 * <pre>
 * {@code
 * IdGeneratorSnowFlakeSupport.getInstance().load();
 * }
 * </pre>
 */
@Slf4j
public final class IdGeneratorSnowFlakeSupport {

	// Make state instance-level and final to avoid racy reassignments
	private final ConcurrentLinkedQueue < Long > queue = new ConcurrentLinkedQueue <> ( );
	private final AtomicInteger queueSize = new AtomicInteger ( 0 );
	private final AtomicBoolean loading = new AtomicBoolean ( false );
	private final Object genLock = new Object ( ); // serialize snowflake.nextId() if needed by library
	private final Snowflake snowflake;

	private static volatile IdGeneratorSnowFlakeSupport INSTANCE;

	// Reasonable defaults; allow overriding via system properties/env if desired
	private static final int MAX_QUEUE_SIZE = 1024;
	private static final int MIN_QUEUE_SIZE_BEFORE_LOAD = 256;
	private static final String PROP_WORKER_ID = "snowflake.workerId";
	private static final String PROP_DATACENTER_ID = "snowflake.datacenterId";

	private IdGeneratorSnowFlakeSupport ( ) {
		// Resolve worker and datacenter ids from system properties (fallback to defaults)
		long workerId = resolveLong ( PROP_WORKER_ID, 25L );
		long datacenterId = resolveLong ( PROP_DATACENTER_ID, 2L );
		this.snowflake = new Snowflake ( workerId, datacenterId );
		loadIfNeeded ( true ); // initial prefill
	}

	private static long resolveLong ( String key, long def ) {
		try {
			String sys = System.getProperty ( key );
			if ( sys != null && ! sys.isBlank ( ) ) return Long.parseLong ( sys.trim ( ) );
			String env = System.getenv ( key.replace ( '.', '_' ).toUpperCase ( ) );
			if ( env != null && ! env.isBlank ( ) ) return Long.parseLong ( env.trim ( ) );
		} catch ( Exception ignore ) {
			// keep default
		}
		return def;
	}

	/**
	 * Returns an instance of the IdGeneratorSnowFlakeSupport class.
	 * <p>
	 * If an instance does not already exists, a new instance is created and returned.
	 * Otherwise, the existing instance is returned.
	 *
	 * @return an instance of the IdGeneratorSnowFlakeSupport class
	 */
	public static IdGeneratorSnowFlakeSupport getINSTANCE ( ) {
		IdGeneratorSnowFlakeSupport local = INSTANCE;
		if ( local == null ) {
			synchronized ( IdGeneratorSnowFlakeSupport.class ) {
				local = INSTANCE;
				if ( local == null ) {
					local = new IdGeneratorSnowFlakeSupport ( );
					INSTANCE = local;
				}
			}
		}
		return local;
	}

	/**
	 * Gets the next id number.
	 * <p>
	 * Polls from the queue if available; otherwise generates a new id.
	 * Triggers an asynchronous top-up when below threshold (single-flight guarded).
	 *
	 * @return the next id number
	 */
	public long getNextId ( ) {
		Long fromQueue = queue.poll ( );
		if ( fromQueue != null ) {
			queueSize.decrementAndGet ( );
			// background top-up when low
			if ( queueSize.get ( ) <= MIN_QUEUE_SIZE_BEFORE_LOAD ) {
				loadIfNeeded ( false );
			}
			return fromQueue;
		}
		// Generate directly if queue empty
		return nextIdSafe ( );
	}

	// Generate one id, serialized if the Snowflake implementation isn't thread-safe
	private long nextIdSafe ( ) {
		synchronized ( genLock ) {
			return snowflake.nextId ( );
		}
	}

	/**
	 * Top-up queue if needed.
	 * If force is true, attempt to fill up to capacity; otherwise, only when below threshold.
	 * Single-flight via AtomicBoolean to avoid concurrent loads.
	 */
	private void loadIfNeeded ( boolean force ) {
		if ( ! force && queueSize.get ( ) > MIN_QUEUE_SIZE_BEFORE_LOAD ) return;
		if ( ! loading.compareAndSet ( false, true ) ) return; // another load in progress
		try {
			int toProduce = Math.max ( 0, MAX_QUEUE_SIZE - queueSize.get ( ) );
			if ( force && toProduce == 0 ) return;

			int produced = 0;
			while ( produced < toProduce ) {
				long id = nextIdSafe ( );
				// Rely on Snowflake uniqueness; no contains() scan
				if ( queue.offer ( id ) ) {
					queueSize.incrementAndGet ( );
					produced++;
				}
			}
			log.debug ( "IdGeneratorSnowFlakeSupport::loadIfNeeded() : queued={}, produced={}", queueSize.get ( ), produced );
		} finally {
			loading.set ( false );
		}
	}
}
