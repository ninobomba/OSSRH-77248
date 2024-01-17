package io.github.ninobomba.commons.exceptions.types.platform;


import lombok.EqualsAndHashCode;

@EqualsAndHashCode ( callSuper = false )
public final class PlatformComponentException extends RuntimeException {
	
	public PlatformComponentException ( ) {
	}
	
	public PlatformComponentException ( String message ) {
		super ( message );
	}
	
	public PlatformComponentException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	public PlatformComponentException ( Throwable cause ) {
		super ( cause );
	}
	
	public PlatformComponentException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
