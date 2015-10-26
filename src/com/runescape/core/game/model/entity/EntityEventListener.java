package com.runescape.core.game.model.entity;

public abstract class EntityEventListener<T extends Entity> {

	/**
	 * Adds an entity into the virtual world.
	 * 
	 * @param entity The target of addition.
	 */
	public abstract void add(T entity);

	/**
	 * Removes an entity from the virtual world.
	 * 
	 * @param entity The target of subtraction.
	 */
	public abstract void remove(T entity);

	/**
	 * Updates an entity that is already present in the virtual world.
	 * 
	 * @param entity The target of the updating procedure.
	 */
	public abstract void update(T entity);
}