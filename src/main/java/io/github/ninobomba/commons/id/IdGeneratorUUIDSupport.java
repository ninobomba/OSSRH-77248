package io.github.ninobomba.commons.id;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;

import java.util.UUID;

public enum IdGeneratorUUIDSupport {

	INSTANCE;

	public String getNextId ( TYPE type ) {
		return switch ( type ) {
			case TIME_BASED_GENERATOR -> Generators.timeBasedGenerator ( ).generate ( ).toString ( );
			case RANDOM_GENERATOR -> Generators.randomBasedGenerator ( ).generate ( ).toString ( );
			case TIME_BASED_REORDERED_GENERATOR -> Generators.timeBasedReorderedGenerator ( ).generate ( ).toString ( );
			case TIME_BASED_EPOCH_GENERATOR -> Generators.timeBasedEpochGenerator ( ).generate ( ).toString ( );
			case ETHERNET_BASED_GENERATOR ->
					Generators.timeBasedGenerator ( EthernetAddress.fromInterface ( ) ).generate ( ).toString ( );
			case TIME_BASED_EPOCH_RANDOM_GENERATOR ->
					Generators.timeBasedEpochRandomGenerator ( ).generate ( ).toString ( );
			default -> UUID.randomUUID ( ).toString ( );
		};
	}

	public enum TYPE {
		DEFAULT_JAVA_RANDOM_UUID,
		TIME_BASED_GENERATOR,
		RANDOM_GENERATOR,
		ETHERNET_BASED_GENERATOR,
		TIME_BASED_REORDERED_GENERATOR,
		TIME_BASED_EPOCH_GENERATOR,
		TIME_BASED_EPOCH_RANDOM_GENERATOR;
	}
}
