package io.github.ninobomba.commons.exceptions.commons;

/**
 * This enum class contains constants related to exceptions.
 */
public enum ExceptionsConstants {
	
	CUSTOM_PACKAGE_NAME ( "io.github.ninobomba.commons.exceptions.types" );
	
	private final String value;
	
	ExceptionsConstants( String value ) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
