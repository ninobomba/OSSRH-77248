package io.github.ninobomba.commons.constants;

public enum UserRolesConstants {

	ROLE_ADMIN ( "ROLE_ADMIN" ),
	ROLE_USER ( "ROLE_USER" );

	private final String value;

	UserRolesConstants ( String value ) {
		this.value = value;
	}

	public String getValue ( ) {
		return value;
	}

}
