package io.github.ninobomba.commons.data.validators;

import jakarta.validation.constraints.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class to compare validation constraints between entity and DTO classes.
 * This class analyzes the annotations on fields and record components to identify
 * discrepancies in validation constraints.
 */
public class ValidationConsistencyChecker {

	/**
	 * Compares validation constraints between an entity class and a DTO class.
	 *
	 * @param entityClass The entity class to check
	 * @param dtoClass    The DTO class to check
	 * @return A list of constraint discrepancies found
	 */
	public static List < ConstraintDiscrepancy > compareEntityWithDto (
			Class < ? > entityClass,
			Class < ? > dtoClass ) {

		List < ConstraintDiscrepancy > discrepancies = new ArrayList <> ( );

		// Get all fields from the entity class
		Field[] entityFields = entityClass.getDeclaredFields ( );

		// Check if DTO is a record
		boolean isDtoRecord = dtoClass.isRecord ( );

		for ( Field entityField : entityFields ) {
			String fieldName = entityField.getName ( );

			// Skip fields that are not relevant for validation comparison
			if ( shouldSkipField ( fieldName ) ) {
				continue;
			}

			// Get entity field constraints
			List < Annotation > entityConstraints = getConstraintAnnotations ( entityField );
			if ( entityConstraints.isEmpty ( ) ) {
				continue; // Skip fields without constraints
			}

			// Get corresponding DTO field or record component
			Optional < List < Annotation > > dtoConstraintsOpt;
			if ( isDtoRecord ) {
				dtoConstraintsOpt = findRecordComponentConstraints ( dtoClass, fieldName );
			} else {
				dtoConstraintsOpt = findFieldConstraints ( dtoClass, fieldName );
			}

			// If DTO field/component exists, compare constraints
			if ( dtoConstraintsOpt.isPresent ( ) ) {
				List < Annotation > dtoConstraints = dtoConstraintsOpt.get ( );
				compareConstraints ( fieldName, entityConstraints, dtoConstraints, discrepancies );
			}
		}

		return discrepancies;
	}

	/**
	 * Determines if a field should be skipped in the comparison.
	 * Typically, we skip fields like id, createdBy, createdDate, etc.
	 */
	private static boolean shouldSkipField ( String fieldName ) {
		List < String > skipFields = List.of (
				"id", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"
		);
		return skipFields.contains ( fieldName );
	}

	/**
	 * Gets all constraint annotations from a field.
	 */
	private static List < Annotation > getConstraintAnnotations ( Field field ) {
		return Arrays.stream ( field.getAnnotations ( ) )
				.filter ( ValidationConsistencyChecker::isConstraintAnnotation )
				.collect ( Collectors.toList ( ) );
	}

	/**
	 * Finds constraint annotations for a field in a class.
	 */
	private static Optional < List < Annotation > > findFieldConstraints ( Class < ? > clazz, String fieldName ) {
		try {
			Field field = clazz.getDeclaredField ( fieldName );
			return Optional.of ( getConstraintAnnotations ( field ) );
		} catch ( NoSuchFieldException e ) {
			return Optional.empty ( );
		}
	}

	/**
	 * Finds constraint annotations for a record component in a record class.
	 */
	private static Optional < List < Annotation > > findRecordComponentConstraints ( Class < ? > recordClass, String componentName ) {
		for ( RecordComponent component : recordClass.getRecordComponents ( ) ) {
			if ( component.getName ( ).equals ( componentName ) ) {
				List < Annotation > annotations = Arrays.stream ( component.getAnnotations ( ) )
						.filter ( ValidationConsistencyChecker::isConstraintAnnotation )
						.collect ( Collectors.toList ( ) );

				// Also check accessor method for annotations
				try {
					Method accessor = recordClass.getDeclaredMethod ( componentName );
					List < Annotation > accessorAnnotations = Arrays.stream ( accessor.getAnnotations ( ) )
							.filter ( ValidationConsistencyChecker::isConstraintAnnotation )
							.collect ( Collectors.toList ( ) );
					annotations.addAll ( accessorAnnotations );
				} catch ( NoSuchMethodException ignored ) {
					// Accessor method not found, continue with component annotations
				}

				return Optional.of ( annotations );
			}
		}
		return Optional.empty ( );
	}

	/**
	 * Determines if an annotation is a validation constraint.
	 */
	private static boolean isConstraintAnnotation ( Annotation annotation ) {
		Class < ? extends Annotation > annotationType = annotation.annotationType ( );
		return annotationType.equals ( NotNull.class ) ||
				annotationType.equals ( NotBlank.class ) ||
				annotationType.equals ( NotEmpty.class ) ||
				annotationType.equals ( Size.class ) ||
				annotationType.equals ( Min.class ) ||
				annotationType.equals ( Max.class ) ||
				annotationType.equals ( Pattern.class ) ||
				annotationType.equals ( Email.class ) ||
				annotationType.equals ( DecimalMin.class ) ||
				annotationType.equals ( DecimalMax.class ) ||
				annotationType.equals ( Past.class ) ||
				annotationType.equals ( Future.class );
	}

	/**
	 * Compares constraints between entity and DTO and adds discrepancies to the list.
	 */
	private static void compareConstraints (
			String fieldName,
			List < Annotation > entityConstraints,
			List < Annotation > dtoConstraints,
			List < ConstraintDiscrepancy > discrepancies ) {

		// Check for missing NotNull/NotBlank
		checkNotNullConstraint ( fieldName, entityConstraints, dtoConstraints, discrepancies );

		// Check for Size constraint differences
		checkSizeConstraint ( fieldName, entityConstraints, dtoConstraints, discrepancies );

		// Check for Pattern constraint differences
		checkPatternConstraint ( fieldName, entityConstraints, dtoConstraints, discrepancies );

		// Check for Email constraint
		checkEmailConstraint ( fieldName, entityConstraints, dtoConstraints, discrepancies );
	}

	/**
	 * Checks for discrepancies in NotNull/NotBlank constraints.
	 */
	private static void checkNotNullConstraint (
			String fieldName,
			List < Annotation > entityConstraints,
			List < Annotation > dtoConstraints,
			List < ConstraintDiscrepancy > discrepancies ) {

		boolean entityHasNotNull = entityConstraints.stream ( )
				.anyMatch ( a -> a instanceof NotNull );
		boolean entityHasNotBlank = entityConstraints.stream ( )
				.anyMatch ( a -> a instanceof NotBlank );

		boolean dtoHasNotNull = dtoConstraints.stream ( )
				.anyMatch ( a -> a instanceof NotNull );
		boolean dtoHasNotBlank = dtoConstraints.stream ( )
				.anyMatch ( a -> a instanceof NotBlank );

		if ( entityHasNotNull && ! dtoHasNotNull && ! dtoHasNotBlank ) {
			discrepancies.add ( new ConstraintDiscrepancy (
					fieldName,
					"@NotNull",
					"missing",
					"Entity has @NotNull constraint but DTO does not"
			) );
		}

		if ( entityHasNotBlank && ! dtoHasNotBlank ) {
			discrepancies.add ( new ConstraintDiscrepancy (
					fieldName,
					"@NotBlank",
					"missing",
					"Entity has @NotBlank constraint but DTO does not"
			) );
		}
	}

	/**
	 * Checks for discrepancies in Size constraints.
	 */
	private static void checkSizeConstraint (
			String fieldName,
			List < Annotation > entityConstraints,
			List < Annotation > dtoConstraints,
			List < ConstraintDiscrepancy > discrepancies ) {

		Optional < Size > entitySize = entityConstraints.stream ( )
				.filter ( a -> a instanceof Size )
				.map ( a -> ( Size ) a )
				.findFirst ( );

		Optional < Size > dtoSize = dtoConstraints.stream ( )
				.filter ( a -> a instanceof Size )
				.map ( a -> ( Size ) a )
				.findFirst ( );

		if ( entitySize.isPresent ( ) && dtoSize.isEmpty ( ) ) {
			Size size = entitySize.get ( );
			discrepancies.add ( new ConstraintDiscrepancy (
					fieldName,
					String.format ( "@Size(min=%d, max=%d)", size.min ( ), size.max ( ) ),
					"missing",
					"Entity has @Size constraint but DTO does not"
			) );
		} else if ( entitySize.isPresent ( ) && dtoSize.isPresent ( ) ) {
			Size eSize = entitySize.get ( );
			Size dSize = dtoSize.get ( );

			if ( eSize.min ( ) != dSize.min ( ) || eSize.max ( ) != dSize.max ( ) ) {
				discrepancies.add ( new ConstraintDiscrepancy (
						fieldName,
						String.format ( "@Size(min=%d, max=%d)", eSize.min ( ), eSize.max ( ) ),
						String.format ( "@Size(min=%d, max=%d)", dSize.min ( ), dSize.max ( ) ),
						"Entity and DTO have different @Size constraints"
				) );
			}
		}
	}

	/**
	 * Checks for discrepancies in Pattern constraints.
	 */
	private static void checkPatternConstraint (
			String fieldName,
			List < Annotation > entityConstraints,
			List < Annotation > dtoConstraints,
			List < ConstraintDiscrepancy > discrepancies ) {

		Optional < Pattern > entityPattern = entityConstraints.stream ( )
				.filter ( a -> a instanceof Pattern )
				.map ( a -> ( Pattern ) a )
				.findFirst ( );

		Optional < Pattern > dtoPattern = dtoConstraints.stream ( )
				.filter ( a -> a instanceof Pattern )
				.map ( a -> ( Pattern ) a )
				.findFirst ( );

		if ( entityPattern.isPresent ( ) && dtoPattern.isEmpty ( ) ) {
			Pattern pattern = entityPattern.get ( );
			discrepancies.add ( new ConstraintDiscrepancy (
					fieldName,
					String.format ( "@Pattern(regexp=\"%s\")", pattern.regexp ( ) ),
					"missing",
					"Entity has @Pattern constraint but DTO does not"
			) );
		} else if ( entityPattern.isPresent ( ) && dtoPattern.isPresent ( ) ) {
			Pattern ePattern = entityPattern.get ( );
			Pattern dPattern = dtoPattern.get ( );

			if ( ! ePattern.regexp ( ).equals ( dPattern.regexp ( ) ) ) {
				discrepancies.add ( new ConstraintDiscrepancy (
						fieldName,
						String.format ( "@Pattern(regexp=\"%s\")", ePattern.regexp ( ) ),
						String.format ( "@Pattern(regexp=\"%s\")", dPattern.regexp ( ) ),
						"Entity and DTO have different @Pattern constraints"
				) );
			}
		}
	}

	/**
	 * Checks for discrepancies in Email constraints.
	 */
	private static void checkEmailConstraint (
			String fieldName,
			List < Annotation > entityConstraints,
			List < Annotation > dtoConstraints,
			List < ConstraintDiscrepancy > discrepancies ) {

		boolean entityHasEmail = entityConstraints.stream ( )
				.anyMatch ( a -> a instanceof Email );

		boolean dtoHasEmail = dtoConstraints.stream ( )
				.anyMatch ( a -> a instanceof Email );

		if ( entityHasEmail && ! dtoHasEmail ) {
			discrepancies.add ( new ConstraintDiscrepancy (
					fieldName,
					"@Email",
					"missing",
					"Entity has @Email constraint but DTO does not"
			) );
		}
	}
}
