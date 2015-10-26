package com.runescape.core.game.model.entity;

public abstract class Entity {

	/**
	 * The current coordinate point the entity is residing on.
	 */
	private Position location = new Position(0, 0, 0);

	/**
	 * The numerical index of the entity. This index is synchronized
	 * with the client and used for entity indication.
	 */
	private int index;

	/**
	 * Returns an instance of the currently registered {@link EntityEventListener}.
	 * 
	 * @return The returned instance. 
	 */
	public abstract EntityEventListener<? extends Entity> getEventListener();

	/**
	 * Returns the current coordinate point of the entity.
	 * 
	 * @return The returned coordinate point.
	 */
	public Position getLocation() {

		return location;
	}

	/**
	 * Modifies the current coordinate point of the entity.
	 * 
	 * @param location The new modification.
	 */
	public void setLocation(Position location) {

		this.location = location;
	}

	/**
	 * Returns the entity's numerical identification index.
	 * 
	 * @return The returned index.
	 */
	public int getIndex() {

		return index;
	}

	/**
	 * Modifies the entity's numerical identification index.
	 * 
	 * @param index The new modification.
	 */
	public void setIndex(int index) {

		this.index = index;
	}
}