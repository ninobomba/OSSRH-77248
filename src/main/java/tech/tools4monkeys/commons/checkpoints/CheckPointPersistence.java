package tech.tools4monkeys.commons.checkpoints;

import lombok.extern.slf4j.Slf4j;
import tech.tools4monkeys.commons.persistence.PersistenceDiskUtils;
import tech.tools4monkeys.commons.properties.LocalPropertiesLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class CheckPointPersistence
{

    private static String outputDirectory;

    private static CheckPointPersistence checkPointPersistence;

    private CheckPointPersistence() {
    }

    public static CheckPointPersistence getInstance()
    {
        log.trace( "CheckPointPersistence::getInstance() >: start" );

        if( Objects.isNull( checkPointPersistence ) ) {
            log.debug( "CheckPointPersistence::getInstance() _: creating unique instance" );
            checkPointPersistence = new CheckPointPersistence();
        }

        log.trace( "CheckPointPersistence::getInstance() <: complete" );

        return checkPointPersistence;
    }

    public void setOutputDirectory( String directory ) {
        outputDirectory = directory;
    }

    public void save( Map<String, CheckPoint> checkPointMap )
    {
        log.trace( "CheckPointPersistence::save() >: start" );
        if( Objects.isNull( checkPointMap ) ) {
            log.warn( "CheckPointPersistence::save() !: checkpoint map is null, returning" );
            return;
        }

        if( StringUtils.isBlank( outputDirectory ) )
        {
            log.debug( "CheckPointPersistence::save() -> output directory is empty loading from properties file. checkpoints.files.path or defaulting to logs/checkpoints" );
            outputDirectory = LocalPropertiesLoader.getInstance().getProperty( "checkpoints.files.path", "logs/checkpoints" );
            log.debug( "CheckPointPersistence::save() -> output directory: {}", outputDirectory );
        }

        var checkpoints = mapToString( checkPointMap );
        log.debug( "CheckPointPersistence::save() _: persisting checkpoint data: {} ", outputDirectory );
        CompletableFuture.runAsync( () -> PersistenceDiskUtils.persist( outputDirectory, checkpoints ) );

        log.trace( "CheckPointPersistence::save() <: complete" );
    }

    public String mapToString( Map<String, CheckPoint> checkPointMap )
    {
        log.trace( "CheckPointPersistence::mapToString() >: start" );
        var checkpoints = new StringJoiner( "," );
        checkPointMap.forEach( ( k, v ) -> checkpoints.add( v.toJsonString() ) );
        log.trace( "CheckPointPersistence::mapToString() <: complete" );
        return checkpoints.toString();
    }

}
