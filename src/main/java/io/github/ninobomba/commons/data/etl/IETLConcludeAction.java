package io.github.ninobomba.commons.data.etl;

/**
 * This interface represents a generic ETL Conclude Action.
 * <p>
 * An ETL Conclude Action is responsible for performing the necessary tasks to conclude an ETL operation.
 * <p>
 * The classes implementing this interface should define their own implementation for the conclude() method.
 */
public interface IETLConcludeAction {

	boolean conclude ( );

}
