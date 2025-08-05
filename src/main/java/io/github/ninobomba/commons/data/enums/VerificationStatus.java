package io.github.ninobomba.commons.data.enums;

/**
 * Enumeration of verification status values.
 * Used for profile and location verification status tracking.
 */
public enum VerificationStatus {
	/**
	 * Default status for new entities that have not been submitted for verification.
	 */
	UNVERIFIED,

	/**
	 * Status for entities that have been submitted for verification and are awaiting review.
	 */
	PENDING,

	/**
	 * Status for entities that have been reviewed and verified as authentic.
	 */
	VERIFIED,

	/**
	 * Status for entities that have been reviewed and rejected as invalid or inappropriate.
	 */
	REJECTED;

	/**
	 * Returns the verification status as a string.
	 *
	 * @return The name of the verification status
	 */
	public String getValue ( ) {
		return this.name ( );
	}

	/**
	 * Converts a string to the corresponding VerificationStatus enum value.
	 *
	 * @param value The string value to convert
	 * @return The corresponding VerificationStatus enum value
	 * @throws IllegalArgumentException if the value is not a valid verification status
	 */
	public static VerificationStatus fromValue ( String value ) {
		for ( VerificationStatus status : VerificationStatus.values ( ) ) {
			if ( status.name ( ).equals ( value ) ) {
				return status;
			}
		}
		throw new IllegalArgumentException ( "Invalid verification status: " + value );
	}
}
