package io.github.ninobomba.commons.data.etl;

/**
 * The DataCleaningAction interface represents an action to clean data.
 */
public interface IETLCleanAction {

	/**
	 * Executes the clean action.
	 * This method is called to perform the clean action on the data.
	 *
	 * @return {@code true} if the clean action was executed successfully, {@code false} otherwise.
	 */
	boolean executeCleanAction ( );

}
