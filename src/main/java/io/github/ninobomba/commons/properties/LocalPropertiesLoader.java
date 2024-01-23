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

@Slf4j
public final class LocalPropertiesLoader {
	
	private static final String DEFAULT_PROPERTIES_PATH = "src/main/resources/custom/";
	
	private static Properties properties;
	
	private static LocalPropertiesLoader instance;
	
	private LocalPropertiesLoader ( ) {
	}
	
	private LocalPropertiesLoader ( String path ) {
		load ( path );
	}
	
	public static LocalPropertiesLoader getInstance ( ) {
		return getInstance ( DEFAULT_PROPERTIES_PATH );
	}
	
	public static LocalPropertiesLoader getInstance ( String path ) {
		if ( Objects.isNull ( instance ) )
			instance = new LocalPropertiesLoader ( StringUtils.isEmpty ( path ) ? DEFAULT_PROPERTIES_PATH : path );
		print ( );
		return instance;
	}
	
	public String getProperty ( String name, String defaultValue ) {
		return Optional.ofNullable ( getProperty ( name ) ).orElse ( defaultValue );
	}
	
	public String getProperty ( String name ) {
		var property = properties.get ( name );
		return Objects.isNull (  property  ) ? null : property.toString ( );
	}
	
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
	
	private static void print ( ) {
		properties
				.entrySet ( )
				.stream ( )
				.filter ( map -> !StringUtils.containsAnyIgnoreCase ( map.getKey ( ).toString ( ), "token", "secret", "password", "key" ) )
				.collect ( Collectors.toMap ( Map.Entry::getKey, Map.Entry::getValue ) )
				.forEach ( ( k, v ) -> log.debug ( "LocalPropertiesLoader::print() _: kv: {}:{}", k, v ) );
	}
	
	private static Set < String > listPropertiesFile ( String path ) throws IOException {
		var response = new HashSet < String > ( );
		
		var paths = Paths.get ( path );
		log.debug ( "LocalPropertiesLoader::listPropertiesFile() _: paths: {}", paths );
		var streamPath = Files.list ( paths );
		
		streamPath.forEach ( e -> {
			var item = path.concat ( e.getFileName ( ).toString ( ) );
			log.debug ( "LocalPropertiesLoader::listPropertiesFile() _: evaluating: {}", item );
			if ( !Files.isDirectory ( e ) && "properties".equals ( FilenameUtils.getExtension ( String.valueOf ( e ) ) ) ) {
				response.add ( item );
			}
		} );
		streamPath.close ( );
		return response;
	}
	
}
