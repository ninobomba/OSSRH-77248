package io.github.ninobomba.commons.exceptions.types.platform;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode ( callSuper = false )
public final class PlatformRedisException extends RuntimeException {
	
	public PlatformRedisException ( ) {
	}
	
	public PlatformRedisException ( String message ) {
		super ( message );
	}
	
	public PlatformRedisException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	public PlatformRedisException ( Throwable cause ) {
		super ( cause );
	}
	
	public PlatformRedisException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
