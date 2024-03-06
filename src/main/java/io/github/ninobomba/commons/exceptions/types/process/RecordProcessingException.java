package io.github.ninobomba.commons.exceptions.types.process;

public class RecordProcessingException extends RuntimeException {
	
	public RecordProcessingException ( ) {
	}
	
	public RecordProcessingException ( String message ) {
		super ( message );
	}
	
	public RecordProcessingException ( String message, Throwable cause ) {
		super ( message, cause );
	}
	
	public RecordProcessingException ( Throwable cause ) {
		super ( cause );
	}
	
	public RecordProcessingException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
	
}
