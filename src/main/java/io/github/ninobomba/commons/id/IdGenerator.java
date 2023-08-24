package io.github.ninobomba.commons.id;

import lombok.SneakyThrows;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public final class IdGenerator
{

    private static final int MAX_QUEUE_SIZE = 10_000;
    private static final int MIN_QUEUE_SIZE_BEFORE_LOAD = 10;

    private static final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();

    private static IdGenerator instance;

    private static final long WAIT_TIME = 10L;

    private IdGenerator() {
        load();
    }

    public static IdGenerator getInstance() {
        if( Objects.isNull( instance ) ) instance = new IdGenerator();
        return instance;
    }

    public long getNextId() {
        if( queue.isEmpty() || queue.size() <= MIN_QUEUE_SIZE_BEFORE_LOAD ) load();
        return queue.isEmpty() ? getNextId() : queue.poll();
    }

    private static void load() {
        IntStream
                .rangeClosed( 1, MAX_QUEUE_SIZE / 2 )
                .forEach( e -> {
                    long id = generateId();
                    if( ! queue.contains( id ) ) queue.offer( id );
                });
    }

    @SneakyThrows
    private static long generateId() {
        TimeUnit.NANOSECONDS.sleep( WAIT_TIME );
        return new AtomicLong( System.currentTimeMillis() )
                .accumulateAndGet( System.currentTimeMillis(), ( previous, next ) -> next > previous ? next : next + 1 );
    }

}
