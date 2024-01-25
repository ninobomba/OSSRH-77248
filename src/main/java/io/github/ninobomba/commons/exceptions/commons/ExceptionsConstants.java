package io.github.ninobomba.commons.exceptions.commons;

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
