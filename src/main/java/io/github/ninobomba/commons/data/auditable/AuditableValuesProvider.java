package io.github.ninobomba.commons.data.auditable;

import lombok.Getter;

import java.lang.reflect.Field;
import java.time.Instant;

public final class AuditableValuesProvider {

	@Getter
	private final static AuditableConstants constants = new AuditableConstants ( );

	private static final String STATUS = "status";
	private static final String CREATED_BY = "createdBy";
	private static final String CREATED_DATE = "createdDate";
	private static final String LAST_MODIFIED_BY = "lastModifiedBy";
	private static final String LAST_MODIFIED_DATE = "lastModifiedDate";

	public enum CRUDConstants {
		CREATE, READ, UPDATE, DELETE, LIST;
	}

	public static void setAuditableValues ( Object entityInstance, String username, CRUDConstants operation ) {
		Field[] fields = entityInstance.getClass ( ).getDeclaredFields ( );

		for ( Field field : fields ) {
			switch ( operation ) {
				case CREATE -> handleCreateOperation ( field, entityInstance, username );
				case DELETE -> handleDeleteOperation ( field, entityInstance, username );
				case UPDATE -> handleUpdateOperation ( field, entityInstance, username );
				default -> throw new IllegalArgumentException ( "Unsupported operation: " + operation );
			}
		}

	}

	private static void handleCreateOperation ( Field field, Object entityInstance, String username ) {
		if ( STATUS.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, constants.getStatusCode ( ) );
		if ( CREATED_BY.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, username );
		if ( CREATED_DATE.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, Instant.now ( ) );
		if ( LAST_MODIFIED_BY.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, username );
		if ( LAST_MODIFIED_DATE.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, Instant.now ( ) );
	}

	private static void handleDeleteOperation ( Field field, Object entityInstance, String username ) {
		if ( STATUS.equals ( field.getName ( ) ) )
			setFieldValue ( field, entityInstance, constants.getStatusCodeDisable ( ) );
		if ( LAST_MODIFIED_BY.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, username );
		if ( LAST_MODIFIED_DATE.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, Instant.now ( ) );
	}

	private static void handleUpdateOperation ( Field field, Object entityInstance, String username ) {
		if ( LAST_MODIFIED_BY.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, username );
		if ( LAST_MODIFIED_DATE.equals ( field.getName ( ) ) ) setFieldValue ( field, entityInstance, Instant.now ( ) );
	}

	private static void setFieldValue ( Field field, Object entityInstance, Object value ) {
		try {
			field.setAccessible ( true );
			field.set ( entityInstance, value );
		} catch ( IllegalAccessException e ) {
			throw new RuntimeException ( "Failed to set value for field: " + field.getName ( ), e );
		}
	}

}
