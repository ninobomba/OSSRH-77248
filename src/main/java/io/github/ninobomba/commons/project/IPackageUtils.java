package io.github.ninobomba.commons.project;

import com.google.common.reflect.ClassPath;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.SubTypes;

public interface IPackageUtils {
	
	static Set < Class < ? extends Throwable > > findAllClassesUsingReflections ( String packageName ) {
		var reflections = new Reflections (
				new ConfigurationBuilder ( )
						.forPackage ( packageName )
						.filterInputsBy ( new FilterBuilder ( ).excludePackage ( "java.lang" ) )
						.setScanners ( SubTypes )
		);
		var classes = reflections.getSubTypesOf ( Throwable.class ).stream ( ).filter ( e -> !e.equals ( RuntimeException.class ) && !e.equals ( Exception.class ) ).collect ( Collectors.toSet ( ) );
		return classes.isEmpty ( ) ? null : classes;
	}
	
	static Set < Class < ? > > findAllClassesUsingGoogleGuice ( String packageName ) throws IOException {
		return ClassPath.from ( ClassLoader.getSystemClassLoader ( ) )
				.getAllClasses ( )
				.stream ( )
				.filter ( clazz -> clazz.getPackageName ( ).equalsIgnoreCase ( packageName ) )
				.map ( ClassPath.ClassInfo::load )
				.collect ( Collectors.toSet ( ) );
	}
	
	static Set < Class < ? > > findAllClassesUsingClassLoader ( String packageName ) {
		InputStream stream = Optional
				.ofNullable ( ClassLoader.getSystemClassLoader ( ).getResourceAsStream ( packageName.replaceAll ( "[.]", "/" ) ) )
				.orElseThrow ( ( ) -> new IllegalArgumentException ( "Package not found" ) );
		
		return new BufferedReader ( new InputStreamReader ( stream ) ).lines ( )
				.filter ( line -> line.endsWith ( ".class" ) )
				.map ( line -> getClass ( line, packageName ) )
				.collect ( Collectors.toSet ( ) );
	}
	
	private static Class < ? > getClass ( String className, String packageName ) {
		try {
			return Class.forName ( packageName + "." + className.substring ( 0, className.lastIndexOf ( '.' ) ) );
		} catch ( ClassNotFoundException e ) {
			// handle the exception
		}
		return null;
	}
	
}
