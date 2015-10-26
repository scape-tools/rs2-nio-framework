package com.runescape.core.game.utility;

public final class IndexContainer {

	/**
	 * The maximum capacity of the container.
	 */
	private final int size;

	/**
	 * The container for both available and occupied index slots.
	 */
	private final Integer[] indexes;

	/**
	 * The overloaded class constructor used for the instantiation of
	 * this class file.
	 * 
	 * @param size The maximum capacity of the container.
	 */
	public IndexContainer(int size) {

		this.size = size;

		this.indexes = new Integer[size];
	}

	/**
	 * Upon removal from the virtual world the index slot an entity previously 
	 * occupied is set to an unoccupied state to allow for a new player to claim it.
	 * 
	 * @param index The previously occupied index.
	 */
	public final void discardIndex(int index){

		getIndexes()[index] = null;
	}

	/**
	 * Returns an encapsulated instance of the index container.
	 * 
	 * @return The returned instance.
	 */
	public final Integer[] getIndexes() {

		return indexes;
	}

	/**
	 * Returns the maximum capacity of the container.
	 * 
	 * @return The returned capacity.
	 */
	public final int getSize() {

		return size;
	}

	/**
	 * Finds an open index slot for an entity to occupy.
	 * 
	 * @return The located slot.
	 */
	public final int calculateIndex() {

		for (int accumulator = 1; accumulator < getIndexes().length; accumulator ++){

			if (getIndexes()[accumulator] == null) {

				getIndexes()[accumulator] = accumulator;

				return getIndexes()[accumulator];
			}
		}

		return -1;
	}
}