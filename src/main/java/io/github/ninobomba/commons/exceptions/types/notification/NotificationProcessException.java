package io.github.ninobomba.commons.exceptions.types.notification;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode ( callSuper = false )
public final class NotificationProcessException extends RuntimeException {
	
	public NotificationProcessException ( ) {
	}
	
	public NotificationProcessException ( String message ) {
		super ( message );
	}
	
	public NotificationProcessException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	public NotificationProcessException ( Throwable cause ) {
		super ( cause );
	}
	
	public NotificationProcessException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
	
}
