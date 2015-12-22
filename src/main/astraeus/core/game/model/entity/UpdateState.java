package main.astraeus.core.game.model.entity;

public enum UpdateState {

	/**
	 * Executes preparations for the main update block.
	 */
	EXECUTE_PRIOR_UPDATE,

	/**
	 * Executes the main update block.
	 */
	EXECUTE_MAIN_UPDATE,

	/**
	 * Executes upon completion of the main update block.
	 */
	EXECUTE_POST_UPDATE;
}