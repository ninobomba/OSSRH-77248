package io.github.ninobomba.commons.data.enums;

/**
 * Enumeration of visibility level values.
 * Used for controlling the visibility of profile information.
 */
public enum VisibilityLevel {
	/**
	 * Visibility restricted to the owner only.
	 */
	PRIVATE,

	/**
	 * Visibility restricted to the owner's contacts or connections.
	 */
	CONTACTS,

	/**
	 * Visibility open to all users of the system.
	 */
	PUBLIC;

	/**
	 * Returns the visibility level as a string.
	 *
	 * @return The name of the visibility level
	 */
	public String getValue ( ) {
		return this.name ( );
	}

	/**
	 * Converts a string to the corresponding VisibilityLevel enum value.
	 *
	 * @param value The string value to convert
	 * @return The corresponding VisibilityLevel enum value
	 * @throws IllegalArgumentException if the value is not a valid visibility level
	 */
	public static VisibilityLevel fromValue ( String value ) {
		for ( VisibilityLevel level : VisibilityLevel.values ( ) ) {
			if ( level.name ( ).equals ( value ) ) {
				return level;
			}
		}
		throw new IllegalArgumentException ( "Invalid visibility level: " + value );
	}
}
