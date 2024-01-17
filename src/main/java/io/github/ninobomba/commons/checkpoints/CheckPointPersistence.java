package io.github.ninobomba.commons.checkpoints;

import io.github.ninobomba.commons.persistence.PersistenceDiskUtils;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class CheckPointPersistence {
	
	private static String outputDirectory;
	
	private static CheckPointPersistence instance;
	
	static {
		log.debug ( "CheckPointPersistence::static-init() -> output directory is empty loading from properties file. checkpoints.files.path or defaulting to logs/checkpoints" );
		outputDirectory = LocalPropertiesLoader.getInstance ( ).getProperty ( "checkpoints.files.path", "logs/checkpoints" );
		log.debug ( "CheckPointPersistence::static-init() -> output directory: {}", outputDirectory );
	}
	
	private CheckPointPersistence ( ) {
	}
	
	/**
	 * Returns the instance of CheckPointPersistence.
	 * If the instance does not exist, it creates a new one.
	 *
	 * @return the instance of CheckPointPersistence
	 */
	public static CheckPointPersistence getInstance ( ) {
		log.trace ( "CheckPointPersistence::getInstance() >: start" );
		
		if ( Objects.isNull ( instance ) ) {
			log.debug ( "CheckPointPersistence::getInstance() _: creating unique instance" );
			instance = new CheckPointPersistence ( );
		}
		
		log.trace ( "CheckPointPersistence::getInstance() <: complete" );
		
		return instance;
	}
	
	/**
	 * Saves the checkpoint data to disk.
	 * If the checkpoint map is empty or null, the method returns without doing anything.
	 * If the output directory is empty, the method returns without doing anything.
	 *
	 * @param checkPointMap the checkpoint map containing the key-value pairs of checkpoint data
	 */
	public void save ( Map < String, CheckPoint > checkPointMap ) {
		log.trace ( "CheckPointPersistence::save() >: start" );
		
		if ( CollectionUtils.isEmpty ( checkPointMap ) ) {
			log.warn ( "CheckPointPersistence::save() !: checkpoint map is null, returning" );
			return;
		}
		
		if ( StringUtils.isBlank ( outputDirectory ) ) {
			log.debug ( "CheckPointPersistence::save() !: output directory is empty, check properties file. checkpoints.files.path" );
			return;
		}
		
		var checkpoints = mapToString ( checkPointMap );
		log.debug ( "CheckPointPersistence::save() _: persisting checkpoint data: {} ", outputDirectory );
		CompletableFuture.runAsync ( ( ) -> PersistenceDiskUtils.persist ( outputDirectory, checkpoints ) );
		
		log.trace ( "CheckPointPersistence::save() <: complete" );
	}
	
	/**
	 * Converts a given checkpoint map to a string representation.
	 * Each checkpoint in the map is converted to a JSON string and joined together with a comma.
	 *
	 * @param checkPointMap the checkpoint map containing the key-value pairs of checkpoint data
	 * @return a string representation of the checkpoint map
	 */
	public String mapToString ( Map < String, CheckPoint > checkPointMap ) {
		log.trace ( "CheckPointPersistence::mapToString() >: start" );
		var checkpoints = new StringJoiner ( "," );
		checkPointMap.forEach ( ( k, v ) -> checkpoints.add ( v.toJsonString ( ) ) );
		log.trace ( "CheckPointPersistence::mapToString() <: complete" );
		return checkpoints.toString ( );
	}
	
}
