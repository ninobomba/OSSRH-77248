package io.github.ninobomba.commons.catalogs.financial;

/**
 * This enum represents a set of common message errors.
 */
public enum CatalogFinancialMessages {

	INVALID_AMOUNT ( "Invalid amount" ),
	BALANCE_IS_NOT_AVAILABLE ( "Balance is not available" ),
	INSUFFICIENT_FUNDS ( "Insufficient funds" );

	public final String description;

	CatalogFinancialMessages ( String description ) {
		this.description = description;
	}

}
