package io.github.ninobomba.commons.unit.exceptions.core.messages;

import io.github.ninobomba.commons.exceptions.core.messages.LocalExceptionMessageBuilder;
import io.github.ninobomba.commons.exceptions.types.process.TransactionProcessException;
import io.github.ninobomba.commons.exceptions.types.system.SystemIllegalAccessException;
import io.github.ninobomba.commons.exceptions.utils.ExceptionUtils;
import io.github.ninobomba.commons.project.IPackageUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class LocalExceptionMessageBuilderTest {

	@Test
	void testInLineInstanceCreator ( ) {

		var message = LocalExceptionMessageBuilder.builder ( )
				.code ( "200" )
				.message ( "OK" )
				.description ( format ( "processed: {}, {}, {}", 5, 6, "C", 1.7 ) )
				//.tClass ( SystemIllegalAccessException.class )
				.throwable ( null )
				.build ( );
		var exception = message.create ( SystemIllegalAccessException.class );

		System.out.println ( exception );

	}

	@Test
	void test ( ) {
		var exception = LocalExceptionMessageBuilder.builder ( )
				.code ( "200" )
				.message ( "OK" )
				.description ( format ( "processed: {}, {}, {}", 1, 2, "A" ) )
				.separator ( LocalExceptionMessageBuilder.SEPARATORS.SYSTEM_SEPARATOR.value )
				.level ( LocalExceptionMessageBuilder.ExceptionLevel.CRITICAL )
				.instance ( "localhost:8181-UUID-ami" )
				.link ( "https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html" )
				.build ( )
				.create ( SystemIllegalAccessException.class );
		System.out.println ( exception );
	}

	@Test
	void testWithThrowable ( ) {
		var exception = LocalExceptionMessageBuilder.builder ( )
				.id ( String.valueOf ( 1_000 ) )
				.code ( "500" )
				.message ( String.format ( "Server Error: %d", 501 ) )
				.description ( "error when running transaction" )
				.throwable ( new Throwable ( "Invalid transaction exception" ) )
				.build ( )
				.create ( TransactionProcessException.class );

		System.out.println ( exception );
	}

	@Test
	void testAll ( ) {

		var classes = IPackageUtils.findAllClassesUsingReflections ( "io.github.ninobomba.commons.exceptions.types" );

		assert classes != null;
		classes.forEach ( e -> {
			var exception = LocalExceptionMessageBuilder.builder ( )
					.code ( "200" )
					.message ( "OK" )
					.description ( "processed" )
					.build ( )
					.create ( SystemIllegalAccessException.class );

			System.out.println ( exception );
		} );
	}

	static String format ( String processingMessage, Object... parameters ) {
		Object[] OBJECT_EMPTY_ARRAY = new Object[] { };
		for ( Object parameter : Optional.ofNullable ( parameters ).orElse ( OBJECT_EMPTY_ARRAY ) )
			processingMessage = processingMessage.replaceFirst ( "\\{}", String.valueOf ( parameter ).replaceAll ( "[^\\dA-Za-z ]", "\\\\$0" ) );
		return processingMessage;
	}

	@Test
	void testWithPropertiesMap ( ) {

		var entries = Map.ofEntries (
				Map.entry ( "id", 1000 ),
				Map.entry ( "code", 500 ),
				Map.entry ( "message", String.format ( "Server Error: %d", 501 ) ),
				Map.entry ( "description", "error when running transaction" ),
				Map.entry ( "throwable", new Throwable ( "Invalid transaction exception" ) )
		);

		var exception = LocalExceptionMessageBuilder.builder ( )
				.properties ( entries )
				.build ( )
				.create ( SystemIllegalAccessException.class );

		assertThat ( exception ).isNotNull ( );

		System.out.println ( exception );

		System.out.println ( ExceptionUtils.toJsonString ( entries, true ) );
		System.out.println ( ExceptionUtils.toJsonString ( entries, false ) );
	}

}
