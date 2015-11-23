package com.astraeus.core.game.model.entity;

import java.util.EnumMap;
import com.astraeus.core.game.model.entity.mobile.Movement;
import com.astraeus.core.game.model.entity.mobile.player.Player.Attributes;

public abstract class Entity {
	
	/**
	 * The map of attributes for a player.
	 */
	protected EnumMap<Attributes, Object> attributes = new EnumMap<>(Attributes.class);	

	/**
	 * @return the attributes
	 */
	public EnumMap<Attributes, Object> getAttributes() {
		return attributes;
	}

	/**
	 * The current coordinate point the entity is residing on.
	 */
	private Position position = new Position(0, 0, 0);
	
	/**
	 * Handles the movement procedures of an entity.
	 */
	private final Movement movement = new Movement(this);

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
	public Position getPosition() {
		return position;
	}

	/**
	 * Modifies the current coordinate point of the entity.
	 * 
	 * @param position The new modification.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * Returns the instance for entity movement control.
	 * 
	 * @return The returned instance.
	 */
	public final Movement getMovement() {
		return movement;
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