package io.github.ninobomba.commons.persistence;

import io.github.ninobomba.commons.time.DateTimeUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The PersistenceDiskUtils class provides utility methods for persisting data to disk.
 */
@Slf4j
public final class PersistenceDiskUtils {
	private static final ReentrantLock lock = new ReentrantLock ( );

	/**
	 *
	 */
	private PersistenceDiskUtils ( ) {
	}

	/**
	 * This method persists the provided data to a file in the specified output directory.
	 *
	 * @param outputDirectory the path of the output directory where the file will be created
	 * @param data            the data to be persisted
	 */
	public static void persist ( String outputDirectory, String data ) {

		createDirectory ( outputDirectory );

		String fileLocation = ""
				.concat ( outputDirectory )
				.concat ( "/" )
				.concat ( DateTimeUtils.getNameByActualTimestamp ( ) )
				.concat ( ".json" );

		var isFileCreated = createFile ( fileLocation );

		if ( ! isFileCreated ) {
			log.warn ( "PersistenceDiskUtils::persist() !: Unable to create file: {} returning ", fileLocation );
			return;
		}

		save ( data, fileLocation );
	}


	/**
	 * Saves the provided data to the specified file location.
	 *
	 * @param data         the data to be saved
	 * @param fileLocation the path of the file to save the data to
	 * @throws IOException if an I/O error occurs while saving the data
	 */
	@SneakyThrows
	private static void save ( String data, String fileLocation ) {

		log.debug ( "PersistenceDiskUtils::save() _: saving data to: {}, size: {} bytes",
				fileLocation,
				data.getBytes ( StandardCharsets.UTF_8 ).length
		);

		try {
			lock.lock ( );
			Files.writeString ( Paths.get ( fileLocation ), data, StandardOpenOption.APPEND );
		} finally {
			lock.unlock ( );
		}

	}

	/**
	 * Create a file at the specified location.
	 *
	 * @param fileLocation the location where the file should be created
	 * @return true if the file was created successfully, false otherwise
	 */
	@SneakyThrows
	private static boolean createFile ( String fileLocation ) {
		log.info ( "PersistenceDiskUtils::createFile() _: saving data to: {}", fileLocation );

		var isFileCreated = true;

		var pathLocation = Paths.get ( fileLocation );

		if ( ! Files.exists ( pathLocation ) ) {
			log.info ( "PersistenceDiskUtils::createFile() _: File does not exists, creating {}, absolute path: {}",
					pathLocation,
					pathLocation.toFile ( ).getAbsolutePath ( )
			);
			isFileCreated = Files.isWritable ( Files.createFile ( pathLocation ) );
		}

		return isFileCreated;
	}

	/**
	 * Creates a directory at the specified outputDirectory if it does not already exist.
	 *
	 * @param outputDirectory the path of the directory to be created
	 */
	@SneakyThrows
	private static void createDirectory ( String outputDirectory ) {

		log.debug ( "PersistenceDiskUtils::createDirectory() _: directory: {}", outputDirectory );

		var path = Paths.get ( outputDirectory );

		if ( Files.exists ( path ) ) {
			log.info ( "PersistenceDiskUtils::createDirectory() _: Path already exists {}. returning", outputDirectory );
			return;
		}

		log.debug ( "PersistenceDiskUtils::createDirectory() _: creating directory: {}, absolute path:{}",
				outputDirectory,
				path.toFile ( ).getAbsolutePath ( )
		);

		if ( ! Files.isWritable ( Files.createDirectories ( path ) ) ) {
			log.error ( "PersistenceDiskUtils::createDirectory() !: Unable to create directory path: ".concat ( path.toString ( ) ) );
			return;
		}

		log.trace ( "PersistenceDiskUtils::createDirectory() <: complete" );
	}

}
