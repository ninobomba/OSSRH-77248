package io.github.ninobomba.commons.id;

import lombok.extern.slf4j.Slf4j;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

@Slf4j
public final class IdGeneratorSnowFlakeSupport
{

    private static final int MAX_QUEUE_SIZE = 20;
    private static final int MIN_QUEUE_SIZE_BEFORE_LOAD = 10;

    private static final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();

    private static final Snowflake snowflake;
    private static IdGeneratorSnowFlakeSupport instance;

    static {
        snowflake = new Snowflake(25, 2 );
        load();
    }

    private IdGeneratorSnowFlakeSupport() {
    }

    public static IdGeneratorSnowFlakeSupport getInstance() {
        if ( Objects.isNull( instance ) ) instance = new IdGeneratorSnowFlakeSupport();
        return instance;
    }

    /**
     * Gets the next id number from the queue.
     *
     * If the queue is empty or the size of the queue is less than or equal to the minimum queue size before load,
     * it will load new id numbers into memory by calling the load method.
     *
     * If the queue is still empty after loading new id numbers, it will generate a new id number using Snowflake algorithm.
     * Otherwise, it will retrieve the next id number from the queue.
     *
     * @return the next id number
     */
    public long getNextId()
    {
        if( queue.isEmpty() || queue.size() <= MIN_QUEUE_SIZE_BEFORE_LOAD ) {
            log.info( "IdGenerator::getNextId() !: loading new id numbers into memory" );
            load();
        }

        return queue.isEmpty() ? snowflake.nextId() : queue.poll();
    }

    /**
     * Loads new id numbers into memory.
     *
     * It generates new id numbers using the Snowflake algorithm and adds them to the queue.
     * The loading process is parallelized to maximize performance.
     *
     * After loading, it logs the number of id numbers that have been loaded into memory.
     */
    private static void load() {
        IntStream
                .rangeClosed( 1, MAX_QUEUE_SIZE )
                .parallel()
                .forEach( e -> {
                    long id = snowflake.nextId();
                    if( ! queue.contains( id ) ) queue.offer( id );
                });

        log.info( "IdGenerator::load() : loaded {} id numbers into memory", queue.size() );
    }

}
