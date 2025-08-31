package io.github.ninobomba.commons.constants.processes;

import lombok.Getter;

public enum ProcessStatus {
	SUCCESS ( true, "SUCCESS" ),
	FAILURE ( false, "FAILURE" );

	@Getter
	private final boolean successful;
	private final String label;

	ProcessStatus ( boolean successful, String label ) {
		this.successful = successful;
		this.label = label;
	}

	public String label ( ) {
		return label;
	}

	public static ProcessStatus fromBoolean ( boolean successful ) {
		return successful ? SUCCESS : FAILURE;
	}

	public static ProcessStatus fromString ( String value ) {
		if ( value == null ) {
			return FAILURE;
		}
		return "SUCCESS".equalsIgnoreCase ( value ) ? SUCCESS : FAILURE;
	}

	@Override
	public String toString ( ) {
		return label;
	}
}
