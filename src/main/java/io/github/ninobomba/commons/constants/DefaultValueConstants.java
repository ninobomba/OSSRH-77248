package io.github.ninobomba.commons.constants;

import java.time.ZoneId;

public interface DefaultValueConstants {

	Object DEFAULT_NULL_VALUE = null;
	Object DEFAULT_OBJECT_INSTANCE = new Object ( );
	
	ZoneId DEFAULT_TIMEZONE = ZoneId.systemDefault ( );

	enum DefaultNumberValues {
		;
		public static final int DEFAULT_INTEGER = 0;
		public static final long DEFAULT_LONG = 0L;
		public static final double DEFAULT_DOUBLE = 0.0;
		public static final boolean DEFAULT_BOOLEAN = false;
	}

	enum DefaultStringValues {
		;
		public static final String DEFAULT_STRING = "";
		public static final String DEFAULT_UNKNOWN_VALUE = "UNKNOWN";
		public static final String DEFAULT_FIELD_NAME = null;
		public static final String DEFAULT_FIELD_VALUE = null;
		public static final String DEFAULT_MESSAGE_VALUE = null;
		public static final String DEFAULT_DESCRIPTION_VALUE = null;
	}
}
