package io.github.ninobomba.commons.id;

import lombok.extern.slf4j.Slf4j;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

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

	private static ConcurrentLinkedQueue < Long > queue = null;

	private static Snowflake snowflake;
	private static IdGeneratorSnowFlakeSupport INSTANCE;

	private static final int MAX_QUEUE_SIZE = 20;
	private static final int MIN_QUEUE_SIZE_BEFORE_LOAD = 10;

	private IdGeneratorSnowFlakeSupport ( ) {
		load ( );
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
		if ( Objects.isNull ( INSTANCE ) ) {
			synchronized ( IdGeneratorSnowFlakeSupport.class ) {
				INSTANCE = new IdGeneratorSnowFlakeSupport ( );
			}
		}
		return INSTANCE;
	}

	/**
	 * Gets the next id number from the queue.
	 * <p>
	 * If the queue is empty or the size of the queue is less than or equal to the minimum queue size before load,
	 * it will load new id numbers into memory by calling the load method.
	 * <p>
	 * If the queue is still empty after loading new id numbers, it will generate a new id number using Snowflake algorithm.
	 * Otherwise, it will retrieve the next id number from the queue.
	 *
	 * @return the next id number
	 */
	public long getNextId ( ) {
		if ( queue.isEmpty ( ) || queue.size ( ) <= MIN_QUEUE_SIZE_BEFORE_LOAD ) {
			log.info ( "IdGeneratorConcurrentLinkedQueueSupport::getNextId() !: loading new id numbers into memory" );
			load ( );
		}
		return Optional.ofNullable ( queue.poll ( ) ).orElse ( snowflake.nextId ( ) );
	}

	/**
	 * Loads new id numbers into memory.
	 * <p>
	 * It generates new id numbers using the Snowflake algorithm and adds them to the queue.
	 * The loading process is parallelized to maximize performance.
	 * <p>
	 * After loading, it logs the number of id numbers that have been loaded into memory.
	 */
	private static void load ( ) {

		snowflake = new Snowflake ( 25, 2 );
		queue = new ConcurrentLinkedQueue <> ( );

		IntStream
				.rangeClosed ( 1, MAX_QUEUE_SIZE )
				.parallel ( )
				.forEach ( e -> {
					long id = snowflake.nextId ( );
					if ( ! queue.contains ( id ) ) queue.offer ( id );
				} );

		log.info ( "IdGeneratorConcurrentLinkedQueueSupport::load() : loaded {} id numbers into memory", queue.size ( ) );
	}

}
