package io.github.ninobomba.commons.exceptions.types.platform;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode ( callSuper = false )
public final class PlatformPostgresqlException extends RuntimeException {
	
	public PlatformPostgresqlException ( ) {
	}
	
	public PlatformPostgresqlException ( String message ) {
		super ( message );
	}
	
	public PlatformPostgresqlException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	public PlatformPostgresqlException ( Throwable cause ) {
		super ( cause );
	}
	
	public PlatformPostgresqlException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
