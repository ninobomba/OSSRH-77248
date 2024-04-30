package io.github.ninobomba.commons.data.etl;

/**
 * This interface represents a generic ETL Conclude Action.
 *
 * An ETL Conclude Action is responsible for performing the necessary tasks to conclude an ETL operation.
 *
 * The classes implementing this interface should define their own implementation for the conclude() method.
 *
 * @return true if the ETL conclude action is successfully executed, otherwise false.
 */
public interface IETLConcludeAction {
	
	boolean conclude();
	
}
