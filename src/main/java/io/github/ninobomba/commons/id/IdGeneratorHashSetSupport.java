package io.github.ninobomba.commons.id;

import lombok.SneakyThrows;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public final class IdGeneratorHashSetSupport {
	
	private static final int MAX_QUEUE_SIZE = 10_000;
	private static final int MIN_QUEUE_SIZE_BEFORE_LOAD = 10;
	private static final long WAIT_TIME = 1L;
	
	private static final Set < Long > queue = ConcurrentHashMap.newKeySet ( );
	
	private static IdGeneratorHashSetSupport instance;
	
	private IdGeneratorHashSetSupport ( ) {
		load ( );
	}
	
	/**
	 * Returns an instance of IdGenerator.
	 *
	 * @return the instance of IdGenerator
	 */
	public static IdGeneratorHashSetSupport getInstance ( ) {
		if ( Objects.isNull ( instance ) ) instance = new IdGeneratorHashSetSupport ( );
		return instance;
	}
	
	public long getNextId ( ) {
		if ( queue.isEmpty ( ) || queue.size ( ) <= MIN_QUEUE_SIZE_BEFORE_LOAD )
			load ( );
		Long id = queue.stream ( ).findAny ( ).orElse ( generateId ( ) );
		queue.remove ( id );
		return id;
	}
	
	/**
	 * Loads the IdGenerator with unique generated IDs.
	 * It generates IDs using the generateId method of IdGenerator class,
	 * limits the stream to half of the MAX_QUEUE_SIZE,
	 * filters out any IDs that already exist in the queue,
	 * and puts the remaining unique IDs into the queue using the offer method.
	 * Note: This method does not return any value.
	 */
	private static void load ( ) {
		LongStream
				.generate ( IdGeneratorHashSetSupport::generateId )
				//.parallel()
				.limit ( MAX_QUEUE_SIZE / 2 )
				.forEach ( queue::add );
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
