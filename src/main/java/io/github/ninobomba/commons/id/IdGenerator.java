package io.github.ninobomba.commons.id;

import lombok.SneakyThrows;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public final class IdGenerator
{

    private static final int MAX_QUEUE_SIZE = 10_000;
    private static final int MIN_QUEUE_SIZE_BEFORE_LOAD = 10;

    private static final long WAIT_TIME = 1L;

    private static final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();

    private static IdGenerator instance;

    private IdGenerator() {
        load();
    }

    public static IdGenerator getInstance() {
        if( Objects.isNull( instance ) ) instance = new IdGenerator();
        return instance;
    }

    public long getNextId() {
        if( queue.isEmpty() || queue.size() <= MIN_QUEUE_SIZE_BEFORE_LOAD ) load();
        return Optional.ofNullable( queue.poll() ).orElse( generateId() );
    }

    private static void load() {
        LongStream
                .generate( IdGenerator::generateId )
                //.parallel()
                .limit(  MAX_QUEUE_SIZE / 2 )
                .distinct()
                .filter( e -> ! queue.contains( e ) )
                .forEach( queue::offer );
    }

    @SneakyThrows
    private static long generateId() {
        TimeUnit.MILLISECONDS.sleep( WAIT_TIME );
        return new AtomicLong( System.currentTimeMillis() ).accumulateAndGet( System.currentTimeMillis(), Math::max );
    }

}
