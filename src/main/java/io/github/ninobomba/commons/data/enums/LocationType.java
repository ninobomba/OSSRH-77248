package io.github.ninobomba.commons.data.enums;

/**
 * Enumeration of location type values.
 * Used for categorizing different types of locations associated with a user.
 */
public enum LocationType {
	/**
	 * Residential location or home address of the user.
	 */
	HOME,

	/**
	 * Primary work location or office address.
	 */
	WORK,

	/**
	 * Secondary or satellite office location.
	 */
	BRANCH,

	/**
	 * Temporary location used for a limited time.
	 */
	TEMPORARY,

	/**
	 * Any other type of location not covered by the specific categories.
	 */
	OTHER;

	/**
	 * Returns the location type as a string.
	 *
	 * @return The name of the location type
	 */
	public String getValue ( ) {
		return this.name ( );
	}

	/**
	 * Converts a string to the corresponding LocationType enum value.
	 *
	 * @param value The string value to convert
	 * @return The corresponding LocationType enum value
	 * @throws IllegalArgumentException if the value is not a valid location type
	 */
	public static LocationType fromValue ( String value ) {
		for ( LocationType type : LocationType.values ( ) ) {
			if ( type.name ( ).equals ( value ) ) {
				return type;
			}
		}
		throw new IllegalArgumentException ( "Invalid location type: " + value );
	}
}
