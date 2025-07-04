package io.github.ninobomba.commons.errors.types.communication;

public class InMemoryCacheCommunicationError extends Error {

	public InMemoryCacheCommunicationError ( ) {
	}

	public InMemoryCacheCommunicationError ( String message ) {
		super ( message );
	}

	public InMemoryCacheCommunicationError ( String message, Throwable cause ) {
		super ( message, cause );
	}

	public InMemoryCacheCommunicationError ( Throwable cause ) {
		super ( cause );
	}

	public InMemoryCacheCommunicationError ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
