package io.github.ninobomba.commons.exceptions.types.api;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode ( callSuper = false )
public final class ApiClientResourceException extends RuntimeException {
	
	
	public ApiClientResourceException ( ) {
	}
	
	public ApiClientResourceException ( String message ) {
		super ( message );
	}
	
	public ApiClientResourceException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	public ApiClientResourceException ( Throwable cause ) {
		super ( cause );
	}
	
	public ApiClientResourceException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
	
}
