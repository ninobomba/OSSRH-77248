package io.github.ninobomba.commons.request;

import io.github.ninobomba.commons.persistence.PersistenceDiskUtils;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The RequestManager will keep a blocking queue and persists event:Event and checkpoint:CheckPoint instances.
 */
@Slf4j
public final class RequestManager implements Runnable {

	private static RequestManager instance;

	private static final int MAX_EVENTS_TAKEN_FROM_QUEUE = 1000;

	private static BlockingQueue < Request > requestQueue;

	private static boolean isEnabled;

	private static long sleepTime;

	private static String outputDirectory;

	/**
	 * Initializes the RequestManager.
	 * This constructor is private to enforce the use of the singleton pattern.
	 * It calls the init() method to perform any necessary initialization tasks.
	 * After initialization, it calls the printConfiguration() method to display the current configuration.
	 */
	private RequestManager ( ) {
		init ( );
		printConfiguration ( );
	}

	/**
	 * Initializes the request manager.
	 */
	private static void init ( ) {
		requestQueue = new LinkedBlockingQueue <> ( );
		isEnabled = Boolean.parseBoolean ( LocalPropertiesLoader.getInstance ( ).getProperty ( "request.manager.enabled", "false" ) );
		sleepTime = Long.parseLong ( LocalPropertiesLoader.getInstance ( ).getProperty ( "request.manager.sleep", "10000" ) );
		outputDirectory = LocalPropertiesLoader.getInstance ( ).getProperty ( "request.manager.logs", "logs/requests" );
	}

	/**
	 * Retrieves the instance of RequestManager.
	 *
	 * @return The singleton instance of RequestManager.
	 */
	public static RequestManager getInstance ( ) {
		if ( Objects.isNull ( instance ) ) instance = new RequestManager ( );
		return instance;
	}

	/**
	 * Adds a request to the requestQueue.
	 *
	 * @param request the request to be added to the requestQueue
	 */
	public static void add ( Request request ) {
		if ( Objects.nonNull ( request ) ) requestQueue.add ( request );
	}

	/**
	 * Checks if the request queue is empty, retrieves and handles requests from the queue,
	 * and then persists the processed requests to disk.
	 */
	@SneakyThrows
	public static void checkOnQueue ( ) {
		log.trace ( "RequestManager::checkOnQueue() >: start" );

		var queueSize = requestQueue.isEmpty ( ) ? 0 : requestQueue.size ( );

		if ( queueSize == 0 ) {
			log.trace ( "RequestManager::checkOnQueue() _: queue is empty, returning" );
			return;
		}

		log.debug ( "RequestManager::checkOnQueue() _: actual queue size: {}", queueSize );

		var data = new StringJoiner ( "\n" );
		int index = 0;
		boolean stopConsuming = false;
		final boolean prettyPrint = false;

		while ( ! stopConsuming ) {
			var request = requestQueue.take ( );
			var json = request.toJsonString ( prettyPrint );
			request.clear ( );

			data.add ( json );
			stopConsuming = requestQueue.isEmpty ( ) || index++ >= MAX_EVENTS_TAKEN_FROM_QUEUE;
			log.debug ( "RequestManager::checkOnQueue() _: Request -> \nOutput: {} \nindex: {} - {} \nQueue Size: {} \nStop Consuming: {}",
					json,
					index,
					MAX_EVENTS_TAKEN_FROM_QUEUE,
					requestQueue.size ( ),
					stopConsuming
			);
		}

		PersistenceDiskUtils.persist ( outputDirectory, data.toString ( ) );

		log.trace ( "RequestManager::checkOnQueue() <: complete" );
	}


	/**
	 * Runs the request manager.
	 * <p>
	 * This method checks if the request manager is enabled. If it is not enabled, a warning message is logged and the method returns.
	 * If the request manager is enabled, it enters a loop to continuously check the request queue. Inside the loop, it logs a debug message
	 * indicating the time to sleep before the next check. Then it calls the 'checkOnQueue()' method to process the requests in the queue.
	 * After processing the queue, it sleeps for the specified time in milliseconds using TimeUnit.SECONDS.sleep(). The loop continues until
	 * the request queue is empty.
	 */
	@SneakyThrows
	@Override
	public void run ( ) {
		if ( ! isEnabled ) {
			log.warn ( "RequestManager::run() !: Request manager is disabled. Check properties file - request.manager.enabled" );
			return;
		}

		while ( ! requestQueue.isEmpty ( ) ) {
			log.debug ( "RequestManager::run() _: Request manager check queue tts: {} ms", sleepTime );
			checkOnQueue ( );
			TimeUnit.SECONDS.sleep ( sleepTime );
		}
	}

	/**
	 * Prints the current configuration settings.
	 * <p>
	 * This method logs the current configuration settings to the trace log level using log4j.
	 * The logged configuration settings include the isEnabled flag, sleepTime value,
	 * and outputDirectory path.
	 */
	public void printConfiguration ( ) {
		log.trace ( "RequestManager::printConfiguration() _: isEnabled: {}", isEnabled );
		log.trace ( "RequestManager::printConfiguration() _: sleepTime: {}", sleepTime );
		log.trace ( "RequestManager::printConfiguration() _: output directory: {}", outputDirectory );
	}

}
