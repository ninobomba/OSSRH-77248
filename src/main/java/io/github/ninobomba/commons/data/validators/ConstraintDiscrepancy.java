package io.github.ninobomba.commons.data.validators;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a discrepancy between entity and DTO field constraints.
 * This record is used to track differences in validation annotations between
 * corresponding fields in entity and DTO classes.
 */
public record ConstraintDiscrepancy(
		String fieldName,
		String entityConstraint,
		String dtoConstraint,
		String description
) {
	/**
	 * Creates a new ConstraintDiscrepancy instance.
	 *
	 * @param fieldName        The name of the field with discrepant constraints
	 * @param entityConstraint The constraint as defined in the entity
	 * @param dtoConstraint    The constraint as defined in the DTO
	 * @param description      A description of the discrepancy
	 */
	public ConstraintDiscrepancy {
		// Validate that all required fields are provided
		if ( fieldName == null || fieldName.isBlank ( ) ) {
			throw new IllegalArgumentException ( "Field name cannot be null or blank" );
		}
		if ( entityConstraint == null ) {
			throw new IllegalArgumentException ( "Entity constraint cannot be null" );
		}
		if ( dtoConstraint == null ) {
			throw new IllegalArgumentException ( "DTO constraint cannot be null" );
		}
	}

	@NotNull
	@Override
	public String toString ( ) {
		return String.format (
				"Field '%s' has discrepancy: Entity=%s, DTO=%s, Description=%s",
				fieldName, entityConstraint, dtoConstraint, description
		);
	}
}
