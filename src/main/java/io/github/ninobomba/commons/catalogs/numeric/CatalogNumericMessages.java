package io.github.ninobomba.commons.catalogs.numeric;

/**
 * This enum represents a set of common message errors.
 */
public enum CatalogNumericMessages {

	UNEXPECTED_VALUE ( "Constraint error - Unexpected value" ),
	LOWER_THAN_ZERO ( "The value is lower than zero" ),
	HIGHER_THAN_ZERO ( "The value is higher than zero" ),
	EQUALS_TO_ZERO ( "The value is equals to zero" );

	public final String description;

	CatalogNumericMessages ( String description ) {
		this.description = description;
	}

}
