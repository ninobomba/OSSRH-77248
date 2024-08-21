package io.github.ninobomba.commons.errors.types.communication;

public class DatabaseCommunicationError extends Error {

	public DatabaseCommunicationError ( ) {
		super ( );
	}

	public DatabaseCommunicationError ( String message ) {
		super ( message );
	}

	public DatabaseCommunicationError ( String message, Throwable cause ) {
		super ( message, cause );
	}

	public DatabaseCommunicationError ( Throwable cause ) {
		super ( cause );
	}

	protected DatabaseCommunicationError ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
