package io.github.ninobomba.commons.patterns.process;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link OperationResult#isFailure()} method.
 * <p>
 * The {@code isFailure()} method is used to determine if an {@link OperationResult}
 * instance represents a failure state.
 */
class OperationResultTest {

	@Test
	void isFailure_shouldReturnTrue_whenInstanceIsFailure1 ( ) {
		// Arrange
		OperationResult < Integer > operationResult = OperationResult.failure ( 1 );

		// Act
		boolean result = operationResult.isFailure ( );

		// Assert
		assertTrue ( operationResult.isFailure ( ) );
		assertFalse ( operationResult.isSuccess ( ) );

		System.out.println ( operationResult );
		System.out.println ( "getOrElse: " + operationResult.getOrElse ( 10 ) );
		System.out.println ( "getOrNull: " + operationResult.getOrNull ( ) );
		
		assertThrows ( RuntimeException.class, operationResult::getOrThrow );
		assertTrue ( result, "isFailure should return true when the instance is a Failure" );
	}


	@Test
	void isFailure_shouldReturnTrue_whenInstanceIsFailure ( ) {
		// Arrange
		OperationResult < String > operationResult = OperationResult.failure ( "Operation failed" );

		// Act
		boolean result = operationResult.isFailure ( );

		// Assert
		assertTrue ( result, "isFailure should return true when the instance is a Failure" );
	}

	@Test
	void isFailure_shouldReturnFalse_whenInstanceIsSuccess ( ) {
		// Arrange
		OperationResult < String > operationResult = OperationResult.success ( "Operation succeeded" );

		// Act
		boolean result = operationResult.isFailure ( );

		// Assert
		assertFalse ( result, "isFailure should return false when the instance is a Success" );
	}

	@Test
	void isFailure_shouldReturnTrue_whenFailureHasNullValue ( ) {
		// Arrange
		OperationResult < Void > operationResult = OperationResult.failure ( null );

		// Act
		boolean result = operationResult.isFailure ( );

		// Assert
		assertTrue ( result, "isFailure should return true even if the Failure instance contains null data" );
	}
}
