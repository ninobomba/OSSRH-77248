package io.github.ninobomba.commons.exceptions.core.factories;

import io.github.ninobomba.commons.exceptions.types.system.SystemIllegalAccessException;
import io.github.ninobomba.commons.project.IPackageUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionFactoryPoolTest {
	
	@Test
	void test ( ) {
		
		var factory = new ExceptionFactoryPool <> ( SystemIllegalAccessException.class );
		
		var pool = factory.getPool ( );
		System.out.println ( pool );
		
		var exception = pool.getObject ( );
		assert exception instanceof SystemIllegalAccessException;
		System.out.println ( exception );
		
		exception.initCause ( new Throwable ( "XYZ" ) );
		System.out.println ( "getCause: " + exception.getCause ( ) );
		
		exception.addSuppressed ( new Throwable ( " Suppressed ACX" ) );
		System.out.println ( "getSuppressed" + ReflectionToStringBuilder.toString ( exception.getSuppressed ( ) ) );
		
		System.out.println ( "getLocalizedMessage: " + exception.getLocalizedMessage ( ) );

//        exception.setMessage( exception, "hola");
		System.out.println ( "getMessage: " + exception.getMessage ( ) );
		
		System.out.println ( "Factory size " + factory.getSize ( ) );
		
		
		factory.returnObject ( pool );
		pool.returnObject ( );
		
		factory.shutdown ( );
		
		Assertions.assertThrows ( SystemIllegalAccessException.class, ( ) -> {
			throw exception;
		} );
		
	}
	
	@Test
	void testAll ( ) {
		var classes = IPackageUtils.findAllClassesUsingReflections ( "io.github.ninobomba.commons.exceptions.types" );
		assert classes != null;
		classes.forEach ( e -> {
			var factoryPool = new ExceptionFactoryPool <> ( e );
			var exception = factoryPool.getPool ( );
			assert exception != null;
			System.out.println ( exception );
		} );
	}
	
	
}
