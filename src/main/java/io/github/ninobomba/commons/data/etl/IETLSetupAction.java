package io.github.ninobomba.commons.data.etl;

/**
 * This interface represents an action that performs setup operations for an ETL process.
 * The implementing classes should define the specific setup logic.
 */
public interface IETLSetupAction {
	
	/**
	 * This method is used to perform the setup operations for the ETL process.
	 * The specific setup logic should be implemented by the class that implements the IETLSetupAction interface.
	 *
	 * <p>
	 * Example usage:
	 * <pre>
	 *     IETLSetupAction setupAction = new MyETLSetupAction();
	 *     setupAction.setup();
	 * </pre>
	 * </p>
	 */
	void setup();
	
}
