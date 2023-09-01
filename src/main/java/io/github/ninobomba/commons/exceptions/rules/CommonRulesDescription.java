package io.github.ninobomba.commons.exceptions.rules;

public enum CommonRulesDescription
{

    UNEXPECTED_VALUE( "Constraint error - Unexpected value" ),

    LOWER_THAN_ZERO( "The value is lower than zero" ),
    HIGHER_THAN_ZERO( "The value is higher than zero" ),

    INVALID_AMOUNT( "Invalid amount" ),
    INSUFFICIENT_FUNDS( "Insufficient funds" )
    ;

    public final String description;

    CommonRulesDescription( String description ) {
        this.description = description;
    }

}
