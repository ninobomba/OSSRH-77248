package io.github.ninobomba.commons.web.agent;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

/**
 * The LoginDevice class represents a login device with its associated details.
 * It is used to store information about a device used for logging into a system.
 *
 * The class implements the Serializable interface to allow the object to be serialized and deserialized.
 */
@Builder
public class LoginDevice implements Serializable {
	
	@Serial
	private static final long serialVersionUID = -5510108553531766246L;
	
	private String id;
	
	private String token;
	private String email;
	
	private String timezone;
	
	private String userAgent;
	private String name;
	private String type;
	private String deviceVersion;
	private String category;
	private String osProducer;
	private String osName;
	
	private String remoteHost;
	
	private String enabled;
}
