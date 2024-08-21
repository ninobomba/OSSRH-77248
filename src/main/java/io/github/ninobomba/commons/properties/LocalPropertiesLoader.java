package io.github.ninobomba.commons.properties;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for loading and accessing local properties.
 */
@Slf4j
public final class LocalPropertiesLoader {

	private static final String DEFAULT_PROPERTIES_PATH = "src/main/resources/custom/";

	private static Properties properties;

	private static LocalPropertiesLoader instance;

	/**
	 * This class represents a Local Properties Loader.
	 */
	private LocalPropertiesLoader ( ) {
	}

	/**
	 * Loads local properties from a specified path.
	 *
	 * @param path The path to the properties file(s) to load.
	 */
	private LocalPropertiesLoader ( String path ) {
		load ( path );
	}

	/**
	 * Retrieves the instance of LocalPropertiesLoader.
	 *
	 * @return The instance of LocalPropertiesLoader.
	 */
	public static LocalPropertiesLoader getInstance ( ) {
		return getInstance ( DEFAULT_PROPERTIES_PATH );
	}

	/**
	 * Retrieves the instance of LocalPropertiesLoader. If an instance
	 * does not exist, a new instance will be created and returned.
	 *
	 * @param path The path to the properties file. If null or empty,
	 *             the default path will be used.
	 * @return The instance of LocalPropertiesLoader.
	 */
	public static LocalPropertiesLoader getInstance ( String path ) {
		if ( Objects.isNull ( instance ) )
			instance = new LocalPropertiesLoader ( StringUtils.isEmpty ( path ) ? DEFAULT_PROPERTIES_PATH : path );
		print ( );
		return instance;
	}

	/**
	 * Retrieves the value of the specified property or returns the default value if the property is not found.
	 *
	 * @param name         the name of the property
	 * @param defaultValue the default value to return if the property is not found
	 * @return the value of the property, or the default value if the property is not found
	 */
	public String getProperty ( String name, String defaultValue ) {
		return Optional.ofNullable ( getProperty ( name ) ).orElse ( defaultValue );
	}

	/**
	 * Retrieves the value of a property with the given name.
	 *
	 * @param name The name of the property to retrieve.
	 * @return The value of the property as a String, or null if the property does not exist.
	 */
	public String getProperty ( String name ) {
		var property = properties.get ( name );
		return Objects.isNull ( property ) ? null : property.toString ( );
	}

	/**
	 * Loads properties from the specified path into the property object.
	 *
	 * @param path The path to the properties files directory.
	 */
	@SneakyThrows
	private static void load ( String path ) {
		properties = new Properties ( );
		listPropertiesFile ( path ).forEach ( e -> {
			try ( var in = new FileInputStream ( ResourceUtils.getFile ( e ) ) ) {
				properties.load ( in );
			} catch ( Exception ignored ) {
			}
		} );
	}

	/**
	 * Prints out the non-sensitive properties stored in the property object.
	 * Sensitive properties such as "token", "secret", "password", and "key" are filtered out.
	 * The key-value pairs of the properties are logged using the log framework at the debug level.
	 */
	private static void print ( ) {
		properties
				.entrySet ( )
				.stream ( )
				.filter ( map -> ! StringUtils.containsAnyIgnoreCase ( map.getKey ( ).toString ( ), "token", "secret", "password", "key" ) )
				.collect ( Collectors.toMap ( Map.Entry::getKey, Map.Entry::getValue ) )
				.forEach ( ( k, v ) -> log.debug ( "LocalPropertiesLoader::print() _: kv: {}:{}", k, v ) );
	}

	/**
	 * A utility method to list properties files in the given path.
	 *
	 * @param path The path to search for properties files
	 * @return A set of strings representing the paths of the properties files
	 * @throws IOException If an I/O error occurs during the operation
	 */
	private static Set < String > listPropertiesFile ( String path ) throws IOException {
		var response = new HashSet < String > ( );

		var paths = Paths.get ( path );
		log.debug ( "LocalPropertiesLoader::listPropertiesFile() _: paths: {}", paths );
		var streamPath = Files.list ( paths );

		streamPath.forEach ( e -> {
			var item = path.concat ( e.getFileName ( ).toString ( ) );
			log.debug ( "LocalPropertiesLoader::listPropertiesFile() _: evaluating: {}", item );
			if ( ! Files.isDirectory ( e ) && "properties".equals ( FilenameUtils.getExtension ( String.valueOf ( e ) ) ) ) {
				response.add ( item );
			}
		} );
		streamPath.close ( );
		return response;
	}

}
