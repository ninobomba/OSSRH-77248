package io.github.ninobomba.commons.data.process;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationResultTest {

	@Test
	void testGetOrThrow_Success_ReturnsData ( ) {
		OperationResult < String > result = OperationResult.success ( "Test Data" );
		assertEquals ( "Test Data", result.getOrThrow ( ) );
	}

	@Test
	void testGetOrThrow_Failure_ThrowsException ( ) {
		OperationResult < String > result = OperationResult.failure ( "Error occurred" );
		IllegalStateException exception = assertThrows (
				IllegalStateException.class,
				result::getOrThrow
		);
		assertEquals ( "OperationResult failed with messages: [Error occurred]", exception.getMessage ( ) );
	}

}
