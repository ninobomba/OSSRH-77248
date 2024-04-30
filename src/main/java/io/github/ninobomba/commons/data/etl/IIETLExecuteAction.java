package io.github.ninobomba.commons.data.etl;

/**
 * The IIETLExecuteAction interface represents an action that can be executed as part of an ETL (Extract, Transform, Load) process.
 * It defines two methods that can be implemented by classes that implement this interface.
 */
public interface IIETLExecuteAction {
	
	/**
	 * Executes the action.
	 *
	 * This method executes the action defined by the implementing class. It performs
	 * the necessary operations as part of the Extract, Transform, Load (ETL) process.
	 * The specific details of the execution are dependent on the implementation.
	 * If any arguments are required for the execution, they should be passed as parameters
	 * when invoking this method.
	 */
	void execute ( );
	
	/**
	 * This method executes the action defined by the implementing class.
	 * It performs the necessary operations as part of the Extract, Transform, Load (ETL) process.
	 * The specific details of the execution are dependent on the implementation.
	 * If any arguments are required for the execution, they should be passed as parameters
	 * when invoking this method.
	 *
	 * @param args The arguments required for the execution of the action.
	 */
	void execute ( String... args );
	
}
