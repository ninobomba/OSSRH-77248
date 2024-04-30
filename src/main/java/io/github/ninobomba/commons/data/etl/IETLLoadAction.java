package io.github.ninobomba.commons.data.etl;

/**
 * The IETLLoadAction interface represents an action to load an ETL (Extract, Transform, Load) process.
 * This interface provides a method to trigger the load process.
 */
public interface IETLLoadAction {
	
	/**
	 * Loads the ETL (Extract, Transform, Load) process.
	 *
	 * @return true if the load process is successful, false otherwise.
	 */
	boolean load ( );
	
}
