package com.astraeus.core.game.model.entity.object;

import com.astraeus.core.game.model.entity.Entity;
import com.astraeus.core.game.model.entity.EntityEventListener;
import com.astraeus.core.game.model.entity.Facing;
import com.astraeus.core.game.model.entity.Position;

/**
 * The class that represents an object in the game world.
 * 
 * @author SeVen
 */
public final class GameObject extends Entity {

	/**
	 * The id of this object.
	 */
	private final int id;

	/**
	 * The position of this object.
	 */
	private final Position position;

	/**
	 * The type of this object.
	 */
	private final int type;

	/**
	 * The facing direction of this object.
	 */
	private final Facing facing;

	/**
	 * Constructs a new {@link GameObject}.
	 * 
	 * @param id
	 * 		The id of this object.
	 * 
	 * @param position
	 * 		The position of this object.
	 * 
	 * @param type
	 * 		The type of this object.
	 * 
	 * @param facing
	 * 		The facing direction of this object.
	 */
	public GameObject(int id, Position position, int type, Facing facing) {
		this.id = id;
		this.position = position;
		this.type = type;
		this.facing = facing;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the facing
	 */
	public Facing getFacing() {
		return facing;
	}

	@Override
	public EntityEventListener<? extends Entity> getEventListener() {
		return null;
	}
}
