package io.github.ninobomba.commons.errors.types.communication;

public class ApiCommunicationError extends Error {

	public ApiCommunicationError ( ) {
	}

	public ApiCommunicationError ( String message ) {
		super ( message );
	}

	public ApiCommunicationError ( String message, Throwable cause ) {
		super ( message, cause );
	}

	public ApiCommunicationError ( Throwable cause ) {
		super ( cause );
	}

	public ApiCommunicationError ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}
}
