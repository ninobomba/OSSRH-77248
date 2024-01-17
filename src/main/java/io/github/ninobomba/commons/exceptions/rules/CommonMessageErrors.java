package io.github.ninobomba.commons.exceptions.rules;

public enum CommonMessageErrors {
	
	UNEXPECTED_VALUE ( "Constraint error - Unexpected value" ),
	LOWER_THAN_ZERO ( "The value is lower than zero" ),
	HIGHER_THAN_ZERO ( "The value is higher than zero" ),
	INVALID_AMOUNT ( "Invalid amount" ),
	INSUFFICIENT_FUNDS ( "Insufficient funds" );
	
	public final String description;
	
	CommonMessageErrors ( String description ) {
		this.description = description;
	}
	
}
