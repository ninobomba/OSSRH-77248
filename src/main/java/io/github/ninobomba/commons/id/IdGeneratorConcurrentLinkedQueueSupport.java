package io.github.ninobomba.commons.id;

import lombok.SneakyThrows;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * The IdGeneratorConcurrentLinkedQueueSupport class is responsible for generating unique IDs.
 * It uses a queue to store generated IDs and reloads the queue when it becomes empty or below a certain threshold.
 * The class follows the singleton pattern, meaning there can only be one instance of IdGeneratorConcurrentLinkedQueueSupport.
 */
public final class IdGeneratorConcurrentLinkedQueueSupport {

	private static ConcurrentLinkedQueue < Long > queue = null;

	private static IdGeneratorConcurrentLinkedQueueSupport INSTANCE;

	private static final int MAX_QUEUE_SIZE = 10_000;
	private static final int MIN_QUEUE_SIZE_BEFORE_LOAD = 10;
	private static final long WAIT_TIME = 1L;

	/**
	 * The IdGeneratorConcurrentLinkedQueueSupport class is responsible for generating unique IDs.
	 * It uses a queue to store generated IDs and reloads the queue when it becomes empty or below a certain threshold.
	 * The class follows the singleton pattern, meaning there can only be one instance of IdGeneratorConcurrentLinkedQueueSupport.
	 */
	private IdGeneratorConcurrentLinkedQueueSupport ( ) {
		load ( );
	}

	/**
	 * Returns an instance of IdGeneratorConcurrentLinkedQueueSupport.
	 *
	 * @return the instance of IdGeneratorConcurrentLinkedQueueSupport
	 */
	public static IdGeneratorConcurrentLinkedQueueSupport getINSTANCE ( ) {
		if ( Objects.isNull ( INSTANCE ) ) {
			synchronized ( IdGeneratorConcurrentLinkedQueueSupport.class ) {
				INSTANCE = new IdGeneratorConcurrentLinkedQueueSupport ( );
			}
		}
		return INSTANCE;
	}

	/**
	 * Returns the next ID from the IdGeneratorConcurrentLinkedQueueSupport queue.
	 * <p>
	 * If the queue is empty or its size is below a certain threshold, the method calls the load() method to reload the queue.
	 * It then retrieves the next ID from the queue using the poll() method.
	 * If the retrieved ID is null, the method calls the generateId() method to generate a new ID.
	 *
	 * @return The next ID from the IdGeneratorConcurrentLinkedQueueSupport queue.
	 */
	public long getNextId ( ) {
		if ( queue.isEmpty ( ) || queue.size ( ) <= MIN_QUEUE_SIZE_BEFORE_LOAD )
			load ( );
		return Optional.ofNullable ( queue.poll ( ) ).orElse ( generateId ( ) );
	}

	/**
	 * Loads the IdGeneratorConcurrentLinkedQueueSupport with unique generated IDs.
	 * It generates IDs using the generateId method of IdGeneratorConcurrentLinkedQueueSupport class,
	 * limits the stream to half of the MAX_QUEUE_SIZE,
	 * filters out any IDs that already exist in the queue,
	 * and puts the remaining unique IDs into the queue using the offer method.
	 * Note: This method does not return any value.
	 */
	private static void load ( ) {
		queue = new ConcurrentLinkedQueue <> ( );
		LongStream
				.generate ( IdGeneratorConcurrentLinkedQueueSupport::generateId )
				//.parallel()
				.limit ( MAX_QUEUE_SIZE / 2 )
				.distinct ( )
				.filter ( e -> ! queue.contains ( e ) )
				.forEach ( queue::offer );
	}

	/**
	 * Generates a unique ID.
	 * The method sleeps for WAIT_TIME milliseconds,
	 * then generates a unique ID by obtaining the current system time in milliseconds
	 * and using it to create a new AtomicLong instance.
	 * The AtomicLong's accumulateAndGet method is called with the current system time and the Math::max function
	 * to ensure that the generated ID is always greater than any previously generated IDs.
	 *
	 * @return The generated unique ID.
	 */
	@SneakyThrows
	private static long generateId ( ) {
		TimeUnit.MILLISECONDS.sleep ( WAIT_TIME );
		return new AtomicLong ( System.currentTimeMillis ( ) ).accumulateAndGet ( System.currentTimeMillis ( ), Math::max );
	}

}
