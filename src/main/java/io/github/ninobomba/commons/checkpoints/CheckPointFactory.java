package io.github.ninobomba.commons.checkpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public final class CheckPointFactory {
	
	private static CheckPointFactory checkPointFactory;
	
	private static Map < String, List < CheckPoint > > checkPointTemplates = null;
	
	private static ObjectMapper mapper = null;
	
	private CheckPointFactory ( ) {
		init ( );
	}
	
	private static void init ( ) {
		mapper = new ObjectMapper ( );
		checkPointTemplates = new TreeMap <> ( );
	}
	
	public static CheckPointFactory getInstance ( ) {
		log.trace ( "CheckPointFactory::getInstance() >: start" );
		
		if ( Objects.isNull ( checkPointFactory ) ) {
			log.debug ( "CheckPointFactory::getInstance() _: creating a unique singleton instance" );
			checkPointFactory = new CheckPointFactory ( );
		}
		
		log.trace ( "CheckPointFactory::getInstance() <: complete" );
		
		return checkPointFactory;
	}
	
	public void build ( String key, String json ) {
		if ( StringUtils.isAnyBlank ( key, json ) ) {
			log.debug ( "CheckPointFactory::build() !: empty or null parameters: key / json content" );
			return;
		}
		
		if ( !checkPointTemplates.containsKey ( key ) ) {
			log.debug ( "CheckPointFactory::build() _: building - with key-name: {}", key );
			var list = buildCheckPointList ( json );
			checkPointTemplates.put ( key, list );
		}
	}
	
	/**
	 * The method will read a local file template for the checkpoint.
	 * Default file location unless specified is: src/main/resources/checkpoint/default.json
	 */
	public void build ( List < String > paths ) {
		log.trace ( "CheckPointFactory::build() >: start" );
		
		if ( CollectionUtils.isEmpty ( paths ) ) {
			log.debug ( "CheckPointFactory::build() !: list of path directories is empty: {}", paths );
			return;
		}
		
		paths.forEach ( path -> {
			log.debug ( "CheckPointFactory::build() _: checking path: {}", path );
			
			var key = Paths
					.get ( path )
					.getFileName ( )
					.toString ( )
					.replace ( ".json", "" );
			
			if ( !checkPointTemplates.containsKey ( key ) ) {
				log.debug ( "CheckPointFactory::build() _: building path: {} with key-name: {}", path, key );
				buildClassFromJsonTemplate ( path, key );
			}
			
		} );
		
		log.trace ( "CheckPointFactory::build() <: complete" );
	}
	
	public Map < String, CheckPoint > getCheckPointMap ( String key ) {
		log.trace ( "CheckPointFactory::getCheckPointList() >: start" );
		var map = new HashMap < String, CheckPoint > ( );
		
		var templateList = checkPointTemplates.get ( key );
		
		if ( Objects.isNull ( templateList ) ) {
			log.warn ( "CheckPointFactory::getCheckPointList() !: template class not found name: {}", key );
			return map;
		}
		
		log.debug ( "CheckPointFactory::getCheckPointList() _: building map instance with key: {}, total checkpoints: {}",
				key,
				templateList.size ( )
		);
		
		for ( var checkPoint : templateList ) {
			try {
				var cp = ( CheckPoint ) checkPoint.clone ( );
				map.put ( cp.getId ( ), cp );
			} catch ( CloneNotSupportedException e ) {
				log.error ( "CheckPointFactory::getCheckPointList() !: failure while creating clone instance", e );
			}
		}
		
		log.trace ( "CheckPointFactory::getCheckPointList() <: complete" );
		
		return map;
	}
	
	@SneakyThrows
	private void buildClassFromJsonTemplate ( String filename, String key ) {
		log.trace ( "CheckPointFactory::buildClassFromJsonTemplate() >: start" );
		
		var json = getJsonFileContent ( filename );
		
		log.debug ( "CheckPointFactory::buildClassFromJsonTemplate() _: Content \nPath: {}\nFilename: {}\nContent:{}",
				filename,
				key,
				json
		);
		
		var checkPointList = buildCheckPointList ( json );
		
		log.debug ( "CheckPointFactory::buildClassFromJsonTemplate() _: checkpoint list from template:" );
		checkPointList.forEach ( e -> log.debug ( "{}", e ) );
		
		checkPointTemplates.put ( key, checkPointList );
		
		log.trace ( "CheckPointFactory::buildClassFromJsonTemplate() <: complete" );
	}
	
	@SneakyThrows
	private List < CheckPoint > buildCheckPointList ( String json ) {
		return mapper.readValue (
				json,
				mapper.getTypeFactory ( ).constructCollectionType ( List.class, CheckPoint.class )
		);
	}
	
	@SneakyThrows
	private String getJsonFileContent ( String filename ) {
		log.trace ( "CheckPointFactory::getJsonFileContent() >: start" );
		
		log.debug ( "CheckPointFactory::getJsonFileContent() _: path: {}", filename );
		
		String content;
		try (
				var resource = new ClassPathResource ( filename ).getInputStream ( ) ;
				var reader = new BufferedReader ( new InputStreamReader ( resource, StandardCharsets.UTF_8 ) )
		) {
			content = reader.lines ( ).collect ( Collectors.joining ( "\n" ) );
		}
		
		log.debug ( "CheckPointFactory::getJsonFileContent() _: Path: {}, Size: {}",
				filename,
				content.getBytes ( StandardCharsets.UTF_8 ).length
		);
		
		log.trace ( "CheckPointFactory::getJsonFileContent() <: complete" );
		
		return content;
	}
	
	public String templateSummary ( String key ) {
		var list = checkPointTemplates.get ( key );
		if ( CollectionUtils.isEmpty ( list ) )
			return "Checkpoint template list for key: [" + key + "] is Empty or Null";
		
		StringJoiner joiner = new StringJoiner ( "\n" );
		list.forEach ( e -> joiner.add ( e.toJsonString ( ) ) );
		return joiner.toString ( );
	}
	
}
