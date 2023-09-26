package io.github.ninobomba.commons.persistence;

import io.github.ninobomba.commons.time.DateTimeUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public final class PersistenceDiskUtils
{
    private static final ReentrantLock lock = new ReentrantLock();

    private PersistenceDiskUtils(){}

    public static void persist( String outputDirectory, String data )
    {
        log.trace( "PersistenceDiskUtils::persist() >: start" );

        createDirectory( outputDirectory );

        String fileLocation = ""
                .concat( outputDirectory )
                .concat( "/" )
                .concat( DateTimeUtils.getNameByActualTimestamp() )
                .concat( ".json" );

        var isFileCreated = createFile( fileLocation );

        if( ! isFileCreated ) {
            log.warn("PersistenceDiskUtils::persist() !: Unable to create file: {} returning ", fileLocation);
            return;
        }

        save( data, fileLocation );

        log.trace( "PersistenceDiskUtils::persist() <: complete" );
    }

    @SneakyThrows
    private static void save( String data, String fileLocation )
    {
        log.trace( "PersistenceDiskUtils::save() >: start" );

        log.debug( "PersistenceDiskUtils::save() _: saving data to: {}, size: {} bytes",
                fileLocation,
                data.getBytes( StandardCharsets.UTF_8 ).length
        );

        try {
            lock.lock();
            Files.writeString( Paths.get( fileLocation ), data, StandardOpenOption.APPEND );
        } finally {
            lock.unlock();
        }

        log.trace( "PersistenceDiskUtils::save() <: complete" );
    }

    @SneakyThrows
    private static boolean createFile( String fileLocation )
    {
        log.info( "PersistenceDiskUtils::createFile() _: saving data to: {}", fileLocation );

        var isFileCreated = true;

        var pathLocation = Paths.get( fileLocation );

        if( ! Files.exists( pathLocation ) ) {
            log.info( "PersistenceDiskUtils::createFile() _: File does not exists, creating {}, absolute path: {}",
                    pathLocation,
                    pathLocation.toFile().getAbsolutePath()
            );
            isFileCreated = Files.isWritable( Files.createFile( pathLocation ) );
        }

        return isFileCreated;
    }

    @SneakyThrows
    private static void createDirectory( String outputDirectory )
    {
        log.trace( "PersistenceDiskUtils::createDirectory() >: start" );

        log.debug( "PersistenceDiskUtils::createDirectory() _: directory: {}", outputDirectory );

        var path = Paths.get( outputDirectory );

        if( Files.exists( path ) ) {
            log.info( "PersistenceDiskUtils::createDirectory() _: Path already exists {}. returning", outputDirectory );
            return;
        }

        log.debug( "PersistenceDiskUtils::createDirectory() _: creating directory: {}, absolute path:{}",
                outputDirectory,
                path.toFile().getAbsolutePath()
        );

        if( ! Files.isWritable( Files.createDirectories( path ) ) ) {
            log.error( "PersistenceDiskUtils::createDirectory() !: Unable to create directory path: ".concat( path.toString() ) );
            return;
        }

        log.trace( "PersistenceDiskUtils::createDirectory() <: complete" );
    }

}
