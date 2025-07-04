package io.github.ninobomba.commons.constants;

import lombok.Getter;

import java.time.ZoneId;

@Getter
public enum DefaultConstants {

	DEFAULT_STRING ( "" ),
	DEFAULT_INTEGER ( 0 ),
	DEFAULT_LONG ( 0L ),
	DEFAULT_DOUBLE ( 0.0 ),
	DEFAULT_BOOLEAN ( false ),

	DEFAULT_TIMEZONE ( ZoneId.systemDefault ( ).toString ( ) );

	private final Object value;

	DefaultConstants ( Object value ) {
		this.value = value;
	}

}
