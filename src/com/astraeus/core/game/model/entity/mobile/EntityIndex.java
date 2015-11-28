package com.astraeus.core.game.model.entity.mobile;

/**
 * @author Dylan Vicchiarelli
 *
 * Handles the indexing of mobile entities in the virtual world. Each entity
 * is required to occupy a unique index which is dispatched to the client
 * upon registration.
 */
public final class EntityIndex {

	/**
	 * A collection of the player occupied index slots in the virtual world.
	 */
	private final static Integer[] playerIndexes = new Integer[2000];

	/**
	 * A collection of the mob occupied index slots in the virtual world.
	 */
	private final static Integer[] mobIndexes = new Integer[6000];

	/**
	 * Upon removal from the virtual world the index slot a player previously
	 * occupied is set as unoccupied to allow for a new player to claim it.
	 * 
	 * @param index The previously occupied index.
	 */
	public static final void discardPlayerIndex(int index){

		getPlayerIndexes()[index] = null;
	}

	/**
	 * Upon removal from the virtual world the index slot a mob previously
	 * occupied is set as unoccupied to allow for a new mob to claim it.
	 * 
	 * @param index The previously occupied index.
	 */
	public static final void discardMobIndex(int index){

		getMobIndexes()[index] = null;
	}

	/**
	 * Returns an encapsulated instance of the player index collection.
	 * 
	 * @return The returned instance encapsulation.
	 */
	public static final Integer[] getPlayerIndexes() {

		return playerIndexes;
	}

	/**
	 * Returns an encapsulated instance of the mob index collection.
	 * 
	 * @return The returned instance encapsulation.
	 */
	public static final Integer[] getMobIndexes() {

		return mobIndexes;
	}

	/**
	 * Finds an open index slot for the connecting player to occupy.
	 * 
	 * @return The result of the operation, the index slot that was found.
	 */
	public static final int findFreePlayerIndex() {

		for (int i = 1; i < getPlayerIndexes().length; i ++){

			if (getPlayerIndexes()[i] == null) {

				getPlayerIndexes()[i] = i;

				return getPlayerIndexes()[i];
			}
		}
		return -1;
	}

	/**
	 * Finds an open index slot for the new mob to occupy.
	 * 
	 * @return The result of the operation, the index slot that was found.
	 */
	public static final int findFreeMobIndex() {

		for (int i = 1; i < getMobIndexes().length; i ++){

			if (getMobIndexes()[i] == null) {

				getMobIndexes()[i] = i;

				return getMobIndexes()[i];
			}
		}
		return -1;
	}
}
