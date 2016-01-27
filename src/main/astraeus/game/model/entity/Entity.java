package main.astraeus.game.model.entity;

import java.util.EnumMap;

import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.mobile.Movement;
import main.astraeus.game.model.entity.mobile.player.Player.Attributes;

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
	
	private transient int id;
	
	/**
	 * The index of an entity in an array.
	 */
	private int slot;

	/**
	 * The current coordinate point the entity is residing on.
	 */
	private Position position = new Position(0, 0, 0);
	
	/**
	 * Handles the movement procedures of an entity.
	 */
	private final Movement movement = new Movement(this);	

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
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @param slot the slot to set
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
}