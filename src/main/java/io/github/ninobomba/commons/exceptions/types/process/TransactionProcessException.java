package io.github.ninobomba.commons.exceptions.types.process;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode ( callSuper = false )
public final class TransactionProcessException extends RuntimeException {
	
	
	public TransactionProcessException ( ) {
	}
	
	public TransactionProcessException ( String message ) {
		super ( message );
	}
	
	public TransactionProcessException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	public TransactionProcessException ( Throwable cause ) {
		super ( cause );
	}
	
	public TransactionProcessException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
