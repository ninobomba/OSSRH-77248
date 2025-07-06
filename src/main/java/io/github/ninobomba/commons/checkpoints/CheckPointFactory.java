package io.github.ninobomba.commons.checkpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The CheckPointFactory class is responsible for creating and managing check point templates.
 */
@Slf4j
public final class CheckPointFactory {

    private static CheckPointFactory instance;

    private static Map< String, List< CheckPoint > > checkPointTemplates;

    private static ObjectMapper mapper;

    private CheckPointFactory ( ) {
        init( );
    }

    /**
     * Initializes the system.
     * This method initializes the system by setting up the necessary objects and variables.
     * It creates a new ObjectMapper and a TreeMap for storing check point templates.
     * Note: This method should only be called once during system initialization.
     */
    private static void init ( ) {
        mapper = new ObjectMapper( );
        checkPointTemplates = new TreeMap<>( );
    }

    /**
     * Returns the instance of the CheckPointFactory class. If the instance is null, a new instance is created. This method ensures that only one instance of the CheckPointFactory class
     * is created.
     *
     * @return an instance of the CheckPointFactory class
     */
    public static CheckPointFactory getInstance ( ) {
        if ( Objects.isNull( instance ) ) {
            log.debug( "CheckPointFactory::getInstance() _: creating a unique singleton instance" );
            instance = new CheckPointFactory( );
        }
        return instance;
    }

    /**
     * Builds a check point template with the given key and JSON content.
     * If the key or JSON content is empty or null, it will log a debug message and return without building the check point template.
     * If the key already exists in the check point templates, it will not build a new one.
     *
     * @param key  The key for the check point template.
     * @param json The JSON content for the check point template.
     */
    public void build ( String key, String json ) {
        if ( StringUtils.isAnyBlank( key, json ) ) {
            log.debug( "CheckPointFactory::build() !: empty or null parameters: key / json content" );
            return;
        }

        if ( ! checkPointTemplates.containsKey( key ) ) {
            var list = buildCheckPointList( json );
            checkPointTemplates.put( key, list );
        }
    }

    /**
     * The method will read a local file template for the checkpoint.
     * The default file location unless specified is: src/main/resources/checkpoint/default.json
     */
    public void build ( List< String > paths ) {
        if ( Objects.isNull( paths ) || paths.isEmpty( ) ) {
            log.debug( "CheckPointFactory::build() !: list of path directories is empty: {}", paths );
            return;
        }

        paths.forEach( path -> {
            log.debug( "CheckPointFactory::build() _: checking path: {}", path );

            var key = Paths
                    .get( path )
                    .getFileName( )
                    .toString( )
                    .replace( ".json", "" );

            if ( ! checkPointTemplates.containsKey( key ) ) {
                log.debug( "CheckPointFactory::build() _: building path: {} with key-name: {}", path, key );
                buildClassFromJsonTemplate( path, key );
            }

        } );

    }

    /**
     * Get the CheckPointMap for the given key.
     *
     * @param key The key to retrieve the CheckPointMap for.
     * @return A Map containing the CheckPoint instances corresponding to the given key.
     * If the key is empty or null, an empty Map will be returned.
     * If the key is not found in the checkPointTemplates, an empty Map will be returned.
     */
    public Map< String, CheckPoint > getCheckPointMap ( String key ) {

        log.trace( "CheckPointFactory::getCheckPointList() >: start" );

        if ( StringUtils.isBlank( key ) ) {
            log.debug( "CheckPointFactory::getCheckPointMap() !: empty or null parameters: key" );
            return Collections.emptyMap( );
        }

        if ( ! checkPointTemplates.containsKey( key ) ) {
            log.debug( "CheckPointFactory::getCheckPointMap() _: key not found: {}", key );
            return Collections.emptyMap( );
        }

        var map = new HashMap< String, CheckPoint >( );

        var templateList = checkPointTemplates.get( key );

        if ( Objects.isNull( templateList ) ) {
            log.warn( "CheckPointFactory::getCheckPointList() !: template class not found name: {}", key );
            return map;
        }

        log.debug( "CheckPointFactory::getCheckPointList() _: building map instance with key: {}, total checkpoints: {}",
                key,
                templateList.size( )
        );

        for ( var checkPoint : templateList ) {
            try {
                var cp = ( CheckPoint ) checkPoint.clone( );
                map.put( cp.getId( ), cp );
            } catch ( CloneNotSupportedException e ) {
                log.error( "CheckPointFactory::getCheckPointList() !: failure while creating clone instance", e );
            }
        }

        log.trace( "CheckPointFactory::getCheckPointList() <: complete" );

        return map;
    }

    /**
     * Reads a JSON file, extracts content, builds a list of checkpoints, and stores it in a map.
     *
     * @param filename the path to the JSON file to read
     * @param key      the identifier to associate with the checkpoint list
     */
    @SneakyThrows
    private void buildClassFromJsonTemplate ( String filename, String key ) {
        log.trace( "CheckPointFactory::buildClassFromJsonTemplate() >: start" );

        var json = getJsonFileContent( filename );

        log.debug( "CheckPointFactory::buildClassFromJsonTemplate() _: Content \nPath: {}\nFilename: {}\nContent:{}",
                filename,
                key,
                json
        );

        var checkPointList = buildCheckPointList( json );

        log.debug( "CheckPointFactory::buildClassFromJsonTemplate() _: checkpoint list from template:" );
        checkPointList.forEach( e -> log.debug( "{}", e ) );

        checkPointTemplates.put( key, checkPointList );

        log.trace( "CheckPointFactory::buildClassFromJsonTemplate() <: complete" );
    }

    /**
     * Builds a list of CheckPoints from a JSON string.
     *
     * @param json the JSON string representing the CheckPoint list
     * @return a List of CheckPoint objects
     */
    @SneakyThrows
    private List< CheckPoint > buildCheckPointList ( String json ) {
        return mapper.readValue(
                json,
                mapper.getTypeFactory( ).constructCollectionType( List.class, CheckPoint.class )
        );
    }

    /**
     * Retrieves the content of a JSON file.
     *
     * @param filename The name of the JSON file.
     * @return The content of the JSON file as a string.
     */
    @SneakyThrows
    private String getJsonFileContent ( String filename ) {
        log.trace( "CheckPointFactory::getJsonFileContent() >: start" );

        log.debug( "CheckPointFactory::getJsonFileContent() _: path: {}", filename );

        String content = null;
        try (
                var resource = new ClassPathResource( filename ).getInputStream( );
                var reader = new BufferedReader( new InputStreamReader( resource, StandardCharsets.UTF_8 ) )
        ) {
            content = reader.lines( ).collect( Collectors.joining( "\n" ) );
        }

        log.debug( "CheckPointFactory::getJsonFileContent() _: Path: {}, Size: {}",
                filename,
                content.getBytes( StandardCharsets.UTF_8 ).length
        );

        log.trace( "CheckPointFactory::getJsonFileContent() <: complete" );

        return content;
    }

    /**
     * Returns a summary of the checkpoint templates associated with the given key.
     *
     * @param key the key for which the checkpoint templates are to be retrieved
     * @return a string representation of the checkpoint template summary
     */
    public String templateSummary ( String key ) {
        var list = checkPointTemplates.get( key );
        if ( Objects.isNull( list ) || list.isEmpty( ) )
            return "Checkpoint template list for key: [" + key + "] is Empty or Null";
        StringJoiner joiner = new StringJoiner( "\n" );
        list.forEach( e -> joiner.add( e.toJsonString( ) ) );
        return joiner.toString( );
    }

}
