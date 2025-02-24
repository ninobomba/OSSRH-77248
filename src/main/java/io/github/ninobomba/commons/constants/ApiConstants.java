package io.github.ninobomba.commons.constants;

import lombok.Getter;

@Getter
public enum ApiConstants {

	V1 ( "V1" ),
	V2 ( "V2" ),

	SUCCESS ( "SUCCESS" ),
	FAILED ( "FAILED" ),

	IN_PROGRESS ( "IN_PROGRESS" ),

	ABORTED ( "ABORTED" ),

	STARTED ( "STARTED" ),

	PROCESSED ( "PROCESSED" ),
	NOT_PROCESSED ( "NOT_PROCESSED" ),


	X_REQUEST_ID ( "X-ResetRequest-ID" );

	private final String value;

	ApiConstants ( String value ) {
		this.value = value;
	}

}
