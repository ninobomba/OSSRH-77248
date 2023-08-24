package io.github.ninobomba.commons.id;

import lombok.extern.slf4j.Slf4j;
import xyz.downgoon.snowflake.Snowflake;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
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

    public long getNextId()
    {
        if( queue.isEmpty() || queue.size() <= MIN_QUEUE_SIZE_BEFORE_LOAD ) {
            log.info( "IdGenerator::getNextId() !: loading new id numbers into memory" );
            load();
        }

        return queue.isEmpty() ? snowflake.nextId() : queue.poll();
    }

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
