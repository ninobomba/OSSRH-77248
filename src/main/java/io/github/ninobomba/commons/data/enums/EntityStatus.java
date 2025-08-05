package io.github.ninobomba.commons.data.enums;

/**
 * Enumeration of entity status values.
 * Used for tracking the status of various entities in the system.
 */
public enum EntityStatus {
	/**
	 * Status for active entities that are available for use.
	 */
	ENABLED,

	/**
	 * Status for inactive entities that are not currently available for use.
	 */
	DISABLED,

	/**
	 * Status for entities that have been temporarily blocked or unavailable.
	 */
	SUSPENDED,

	/**
	 * Status for entities that are being phased out (specific to services).
	 */
	DEPRECATED;

	/**
	 * Returns the entity status as a string.
	 *
	 * @return The name of the entity status
	 */
	public String getValue ( ) {
		return this.name ( );
	}

	/**
	 * Converts a string to the corresponding EntityStatus enum value.
	 *
	 * @param value The string value to convert
	 * @return The corresponding EntityStatus enum value
	 * @throws IllegalArgumentException if the value is not a valid entity status
	 */
	public static EntityStatus fromValue ( String value ) {
		for ( EntityStatus status : EntityStatus.values ( ) ) {
			if ( status.name ( ).equals ( value ) ) {
				return status;
			}
		}
		throw new IllegalArgumentException ( "Invalid entity status: " + value );
	}
}
