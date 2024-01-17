package io.github.ninobomba.commons.platform;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public class LoginDevice implements Serializable {
	
	/**
	 *
	 */
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
