package io.github.ninobomba.commons.project;

import com.google.common.reflect.ClassPath;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.reflections.scanners.Scanners.SubTypes;

/**
 * IPackageUtils is an interface that provides utility methods for working with packages and classes.
 */
public interface IPackageUtils {
	
	/**
	 * Finds all classes in the given package using reflection.
	 *
	 * @param packageName the name of the package to search for classes
	 * @return a set of classes that are subtypes of Throwable and not equal to RuntimeException and Exception,
	 * or null if no classes are found
	 */
	static Set < Class < ? extends Throwable > > findAllClassesUsingReflections ( String packageName ) {
		var reflections = new Reflections (
				new ConfigurationBuilder ( )
						.forPackage ( packageName )
						.filterInputsBy ( new FilterBuilder ( ).excludePackage ( "java.lang" ) )
						.setScanners ( SubTypes )
		);
		var classes = reflections.getSubTypesOf ( Throwable.class ).stream ( ).filter ( e -> !e.equals ( RuntimeException.class ) && !e.equals ( Exception.class ) ).collect ( toSet ( ) );
		return classes.isEmpty ( ) ? null : classes;
	}
	
	/**
	 * Finds all classes in the given package using Google Guice.
	 *
	 * @param packageName the name of the package to search for classes
	 * @return a set of classes found in the package
	 * @throws IOException if an I/O error occurs
	 */
	static Set < Class < ? > > findAllClassesUsingGoogleGuice ( String packageName ) throws IOException {
		return ClassPath.from ( ClassLoader.getSystemClassLoader ( ) )
				.getAllClasses ( )
				.stream ( )
				.filter ( clazz -> clazz.getPackageName ( ).equalsIgnoreCase ( packageName ) )
				.map ( ClassPath.ClassInfo::load )
				.collect ( toSet ( ) );
	}
	
	/**
	 * Finds all classes in the given package using the ClassLoader.
	 *
	 * @param packageName the name of the package to search for classes
	 * @return a set of classes found in the package
	 * @throws IllegalArgumentException if the package is not found
	 */
	static Set < Class < ? > > findAllClassesUsingClassLoader ( String packageName ) {
		InputStream stream = Optional
				.ofNullable ( ClassLoader.getSystemClassLoader ( ).getResourceAsStream ( packageName.replaceAll ( "[.]", "/" ) ) )
				.orElseThrow ( ( ) -> new IllegalArgumentException ( "Package not found" ) );
		
		return new BufferedReader ( new InputStreamReader ( stream ) ).lines ( )
				.filter ( line -> line.endsWith ( ".class" ) )
				.map ( line -> getClass ( line, packageName ) )
				.collect ( toSet ( ) );
	}
	
	/**
	 * Retrieves the Class object for a given class name and package name.
	 *
	 * @param className   the name of the class
	 * @param packageName the name of the package
	 * @return the Class object for the specified class, or null if the class is not found
	 */
	private static Class < ? > getClass ( String className, String packageName ) {
		try {
			return Class.forName ( packageName + "." + className.substring ( 0, className.lastIndexOf ( '.' ) ) );
		} catch ( ClassNotFoundException e ) {
			// handle the exception
		}
		return null;
	}
	
	/**
	 * Finds all package names starting with the specified prefix.
	 *
	 * @param prefix the prefix to match package names against
	 * @return a set of package names starting with the specified prefix
	 */
	static Set < String > findPackageNamesStartingWith ( String prefix ) {
		return Arrays.stream ( Package.getPackages ( ) ).toList ( ).stream ( )
				.map ( Package::getName )
				.filter ( n -> n.startsWith ( prefix ) )
				.collect ( toSet ( ) );
	}
	
}
