package io.github.ninobomba.commons.errors.types.configuration;

public class ConfigurationError extends Error {

	public ConfigurationError ( ) {
	}

	public ConfigurationError ( String message ) {
		super ( message );
	}

	public ConfigurationError ( String message, Throwable cause ) {
		super ( message, cause );
	}

	public ConfigurationError ( Throwable cause ) {
		super ( cause );
	}

	public ConfigurationError ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
	
}
