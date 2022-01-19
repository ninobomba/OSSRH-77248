package tech.tools4monkeys.commons.request;

import lombok.extern.slf4j.Slf4j;
import tech.tools4monkeys.commons.persistence.PersistenceDiskUtils;
import tech.tools4monkeys.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The RequestManager will keep a blocking queue and persists event:Event and checkpoint:CheckPoint instances.
 */
@Slf4j
public final class RequestManager implements Runnable
{
    private static final int MAX_EVENTS_TAKEN_FROM_QUEUE = 1000;

    private static final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();

    private static RequestManager instance;

    private final boolean isEnabled;

    private final long sleepTime;

    private static String outputDirectory;

    private RequestManager()
    {
        isEnabled = Boolean.parseBoolean( LocalPropertiesLoader.getInstance().getProperty( "request.manager.enabled", "false" ) );
        sleepTime = Long.parseLong( LocalPropertiesLoader.getInstance().getProperty( "request.manager.sleep", "10000" ) );
        outputDirectory = LocalPropertiesLoader.getInstance().getProperty( "request.manager.logs", "logs/requests" );

        printConfiguration();
    }

    public static RequestManager getInstance()
    {
        if( Objects.isNull( instance ) )
        {
            log.debug( "RequestManager::getInstance() _: creating singleton unique instance" );
            instance = new RequestManager();
        }
        return instance;
    }

    public static void add(Request request) {
        if( Objects.nonNull( request ) ) requestQueue.add( request );
    }

    @SneakyThrows
    public static void checkOnQueue()
    {
        log.trace( "RequestManager::checkOnQueue() >: start" );

        var queueSize = requestQueue.isEmpty() ? 0 : requestQueue.size();

        if( queueSize == 0 ) {
            log.trace( "RequestManager::checkOnQueue() _: queue is empty, returning" );
            return;
        }

        log.debug( "RequestManager::checkOnQueue() _: actual queue size: {}", queueSize );

        var data = new StringJoiner("\n" );
        int index = 0;
        boolean stopConsuming = false;
        final boolean prettyPrint = false;

        while( ! stopConsuming )
        {
            var request = requestQueue.take();
            var json = request.toJsonString( prettyPrint );
            request.clear();

            data.add( json );
            stopConsuming = requestQueue.isEmpty() || index++ >= MAX_EVENTS_TAKEN_FROM_QUEUE;
            log.debug( "RequestManager::checkOnQueue() _: Request -> \nOutput: {} \nindex: {} - {} \nQueue Size: {} \nStop Consuming: {}",
                    json,
                    index,
                    MAX_EVENTS_TAKEN_FROM_QUEUE,
                    requestQueue.size(),
                    stopConsuming
            );
        }

        PersistenceDiskUtils.persist( outputDirectory, data.toString() );

        log.trace( "RequestManager::checkOnQueue() <: complete" );
    }


    @SneakyThrows
    @Override
    public void run()
    {
        if( ! isEnabled ) {
            log.warn( "RequestManager::run() !: Request manager is disabled. Check properties file - request.manager.enabled" );
            return;
        }

        while( isEnabled ) {
            log.debug( "RequestManager::run() _: Request manager check queue tts: {} ms", sleepTime );
            checkOnQueue();
            Thread.sleep( sleepTime );
        }
    }

    public void printConfiguration()
    {
        log.trace( "RequestManager::printConfiguration() _: isEnabled: {}", isEnabled );
        log.trace( "RequestManager::printConfiguration() _: sleepTime: {}", sleepTime );
        log.trace( "RequestManager::printConfiguration() _: output directory: {}", outputDirectory );
    }

}



