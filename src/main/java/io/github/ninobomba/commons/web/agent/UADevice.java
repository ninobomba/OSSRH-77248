package io.github.ninobomba.commons.web.agent;

/**
 * The UADevice class represents a login device with its associated details.
 * It is used to store information about a device used for logging into a system.
 * <p>
 * The class implements the Serializable interface to allow the object to be serialized and deserialized.
 */

public record UADevice(

		String id,

		String token,
		String username,

		String timezone,

		String remoteHost,
		String userAgent,
		String type,

		String name,
		String category,
		
		String osProducer,
		String osName,
		String osVersion,
		String osVersionExtension
) {
}
