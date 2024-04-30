package io.github.ninobomba.commons.data.etl;

/**
 * The IETLPersistAction interface represents an action that can be persisted.
 * Implementing classes should provide logic to persist the action.
 */
public interface IETLPersistAction {
	
	/**
	 * The persist method is used to persist an action.
	 *
	 * @return True if the action was persisted successfully, false otherwise.
	 */
	boolean persist();
	
}
