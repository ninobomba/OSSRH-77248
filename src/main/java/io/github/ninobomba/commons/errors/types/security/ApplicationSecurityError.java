package io.github.ninobomba.commons.errors.types.security;

public class ApplicationSecurityError extends Error {

	public ApplicationSecurityError ( ) {
	}

	public ApplicationSecurityError ( String message ) {
		super ( message );
	}

	public ApplicationSecurityError ( String message, Throwable cause ) {
		super ( message, cause );
	}

	public ApplicationSecurityError ( Throwable cause ) {
		super ( cause );
	}

	public ApplicationSecurityError ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
		super ( message, cause, enableSuppression, writableStackTrace );
	}

}
